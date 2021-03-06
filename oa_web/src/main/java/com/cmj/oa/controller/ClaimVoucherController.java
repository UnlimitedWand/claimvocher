package com.cmj.oa.controller;

import com.cmj.oa.biz.ClaimVoucherBiz;
import com.cmj.oa.biz.EmployeeBiz;
import com.cmj.oa.dto.ClaimItemsParsed;
import com.cmj.oa.dto.ClaimVoucherInfo;
import com.cmj.oa.entity.ClaimVoucher;
import com.cmj.oa.entity.ClaimVoucherItem;
import com.cmj.oa.entity.DealRecord;
import com.cmj.oa.entity.Employee;
import com.cmj.oa.global.Contant;
import com.cmj.oa.util.DateUtil;
import com.cmj.oa.util.FileUtil;
import com.cmj.oa.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

@Controller("claimVoucherController")
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {

    @Autowired
    private ClaimVoucherBiz claimVoucherBiz;

    @Autowired
    private EmployeeBiz employeeBiz;


    @RequestMapping("/receive")
    public String scan_qrcode(){

        return "receive_voucher";
    }

    @RequestMapping("/getImg")
    public void getImg(String imgname,HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            String webPath=request.getServletContext().getRealPath("/");
            System.out.println(webPath+"assets\\img\\"+imgname);
            fis = new FileInputStream(webPath+"assets\\img\\"+imgname);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("items", Contant.getItems());
        map.put("guahaoTypes", Contant.getGuahaoTypes());
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }

    @RequestMapping("/add")
    public String add(HttpSession session, ClaimVoucherInfo info, HttpServletRequest request)throws Exception{//保存报销单
        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest)request;
        System.out.println(mhs);
        String basePath=request.getServletContext().getRealPath("/")+"assets\\img\\";//存储的文件夹基本路径
        String document="";
        int len = info.getItems().size();
        System.out.println(len);
        for(int i = 0; i < len; i++){
            System.out.println("pictures["+i+"]");
            //获取每一个Item对应的图片列表 列表的名字通过picture+i来拼接,完美解决哈哈哈哈哈
            List<MultipartFile> multipartFiles = mhs.getFiles("pictures["+i+"]");
            //判断文件是否为空
            if(multipartFiles.size()!=0){
                System.out.println(multipartFiles.size());
                for (MultipartFile s :multipartFiles) {
                    //获得原文件名
                    String originalName = s.getOriginalFilename();
                    //File.separator表示在 UNIX 系统上，此字段的值为 /；在 Windows 系统上，它为 \，如：C:\tmp\test.txt和tmp/test.txt
                    //获得当前日期
                    Calendar ca = Calendar.getInstance();
                    //拼接日期文件夹
                    //按日期命名文件
                    String filedir = "" + ca.get(ca.YEAR) + (ca.get(ca.MONTH)+1) + ca.get(ca.DATE);
                    //文件名等于客户端IP地址+系统当前毫秒数组成
                    String fileName = filedir + request.getRemoteAddr().replace(":","") + System.currentTimeMillis() + originalName.substring(originalName.lastIndexOf("."));
                    // 复制本地文件到服务器
                    FileCopyUtils.copy(s.getBytes(), new File(basePath + File.separator + fileName));
                    // 将文件保存路径放到request作用域
                    if(document.equals("")){
                        document += fileName;
                    }else
                        document += ":"+ fileName;
                }
                System.out.println("文件上传成功");
            }else{
                System.out.println("文件上传异常");
            }
            info.getItems().get(i).setDocument(document);
            document = "";
        }
        Employee employee = (Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.save(info.getClaimVoucher(),info.getItems());
        return "redirect:detail?id="+info.getClaimVoucher().getId();
    }

    @RequestMapping("/detail")
    public String detail(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBiz.get(id));
        int len = claimVoucherBiz.getItems(id).size();
        List<ClaimItemsParsed> list = new ArrayList<ClaimItemsParsed>();
        for(int i = 0;i < len; i++){
            list.add(new ClaimItemsParsed(claimVoucherBiz.getItems(id).get(i)));
        }
        map.put("items", list);//items里边需要把document分割成list
        map.put("records",claimVoucherBiz.getRecords(id));
        return "claim_voucher_detail";
    }

    @RequestMapping("/self")
    public String self(HttpSession session,Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    @RequestMapping("/deal")
    public String deal(HttpSession session,Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForDeal(employee.getSn()));//获取特定人员的可以处理的一批报销单
        return "claim_voucher_deal";
    }

    @RequestMapping("/to_update")
    public String toUpdate(int id,Map<String,Object> map){
        map.put("items",Contant.getItems());
        ClaimVoucherInfo info = new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherBiz.get(id));
        info.setItems(claimVoucherBiz.getItems(id));
        map.put("info",info);
        return "claim_voucher_update";
    }

    @RequestMapping("/update")
    public String update(HttpSession session,ClaimVoucherInfo info){
        Employee employee = (Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.update(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }

    @RequestMapping("/submit")
    public String submit(int id){
        claimVoucherBiz.submit(id);
        return "redirect:deal";
    }

    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBiz.get(id));

        int len = claimVoucherBiz.getItems(id).size();
        List<ClaimItemsParsed> list = new ArrayList<ClaimItemsParsed>();
        for(int i = 0;i < len; i++){
            list.add(new ClaimItemsParsed(claimVoucherBiz.getItems(id).get(i)));
        }
        map.put("items", list);//items里边需要把document分割成list

        map.put("records",claimVoucherBiz.getRecords(id));
        DealRecord dealRecord =new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
        return "claim_voucher_check";
    }

    @RequestMapping("/check")
    public String check(HttpSession session, DealRecord dealRecord){
        Employee employee = (Employee)session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherBiz.deal(dealRecord);
        return "claim_voucher_deal";
    }

    @RequestMapping("/fuzzyQuery")
    public String fuzzyQuery(HttpSession session,Map<String,Object> map,@RequestParam String status,@RequestParam String total_amount,@RequestParam String create_time ,@RequestParam String cause) throws ParseException, IOException {
        Employee employee = (Employee)session.getAttribute("employee");
        String createSn = employee.getSn();
        Double totalAmount = null;
        if (!total_amount.isEmpty() && total_amount != null){
            totalAmount = Double.valueOf(total_amount);
        }
        map.put("list",claimVoucherBiz.fuzzyQuery(createSn,status,totalAmount,create_time,cause));
        return "claim_voucher_self";
    }

    @RequestMapping("/fuzzyQueryForDeal")
    public String fuzzyQueryForDeal(HttpSession session,Map<String,Object> map,@RequestParam String createSn,@RequestParam String status,@RequestParam String total_amount,@RequestParam String create_time, @RequestParam String cause){
        Employee employee = (Employee)session.getAttribute("employee");
        String nextDealSn = employee.getSn();
        Double totalAmount = null;
        if (!total_amount.isEmpty() && total_amount != null){
            totalAmount = Double.valueOf(total_amount);
        }
        map.put("list",claimVoucherBiz.fuzzyQueryForDeal(createSn,nextDealSn,status,totalAmount,create_time,cause));
        return "claim_voucher_deal";
    }



    @RequestMapping("/delete")
    @ResponseBody
    public String deleteClaimVoucher(@RequestParam("ids") String ids){
        if (ids.length()==0 || ids.isEmpty()){
            return null;
        }
        String[] str = ids.split(",");
        List<Integer> id = new ArrayList<>();
        for (int i = 0 ; i < str.length ; i++){
            id.add(Integer.parseInt(str[i]));
        }
        for (Integer i : id){
            claimVoucherBiz.deleteById(i);
        }
        return null;
    }

    @RequestMapping(value = "/export")
    public String exportExcel(HttpServletResponse response, HttpServletRequest  request,String ids) throws Exception {
        if (ids.length()==0 || ids.isEmpty()){
            return null;
        }
        String[] str = ids.split(",");
        List<Integer> id = new ArrayList<>();
        for (int i = 0 ; i < str.length ; i++){
            id.add(Integer.parseInt(str[i]));
        }
        List<ClaimVoucher> claimVoucherList = new ArrayList<>();
        for (Integer i : id) {
            claimVoucherList.add(claimVoucherBiz.get(i));
        }
        String webPath=request.getServletContext().getRealPath("/");
        Workbook wb = fillExcelDataWithTemplate(claimVoucherList, webPath+"/static/excel/claim.xls");

        ResponseUtil.export(response,wb,"报销单.xls");
        return null;
    }

    public Workbook fillExcelDataWithTemplate(List<ClaimVoucher> list , String templateFileUrl) {
        Workbook wb = null ;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templateFileUrl));
            wb = new HSSFWorkbook(fs);
            // 取得 模板的 第一个sheet 页
            Sheet sheet = wb.getSheetAt(0);
            // 拿到sheet页有多少列
            int cellNums = sheet.getRow(0).getLastCellNum();
            // 从第3行 开始写入 前两行为标题
            int rowIndex = 2;
            Row row = sheet.createRow(rowIndex);
            DateFormat df = DateFormat.getDateTimeInstance();
            for(ClaimVoucher claimVoucher : list){
                row.createCell(0).setCellValue(claimVoucher.getId());
                row.createCell(1).setCellValue(claimVoucher.getStatus());
                row.createCell(2).setCellValue(claimVoucher.getCause());
                row.createCell(3).setCellValue(employeeBiz.getEmployeeName(claimVoucher.getCreateSn()));
                row.createCell(4).setCellValue(claimVoucher.getTotalAmount());
                row.createCell(5).setCellValue(df.format(claimVoucher.getCreateTime()));
                if (claimVoucherBiz.getRecords(claimVoucher.getId()).size()==0){
                    rowIndex++;
                    row = sheet.createRow(rowIndex);
                    continue;
                }else {
                    for (DealRecord dealRecord : claimVoucherBiz.getRecords(claimVoucher.getId())){
                        row.createCell(6).setCellValue(employeeBiz.getEmployeeName(dealRecord.getDealSn()));
                        row.createCell(7).setCellValue(df.format(dealRecord.getDealTime()));
                        row.createCell(8).setCellValue(dealRecord.getDealWay());
                        row.createCell(9).setCellValue(dealRecord.getComment());
                        rowIndex ++;
                        row = sheet.createRow(rowIndex);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }


    @ResponseBody
    @RequestMapping(value = "/upload_excel")
    public JSONObject upload_excel(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request)throws Exception {
        JSONObject result = new JSONObject();

        System.out.println(file.getOriginalFilename());

        if (file.isEmpty()){
            result.put("success", false);
            result.put("msg", "文件为空");
            return result;
        }else {
            String webPath=request.getServletContext().getRealPath("");
            String filePath= "/static/upload_file/excel/";
            //把文件名换成（时间戳.png）
            String fileName=DateUtil.formatDate(new Date(), "yyyyMMdd-HHmmssSSS")+"_"+file.getOriginalFilename();

            FileUtil.makeDirs(webPath+filePath);
            //保存服务器
            file.transferTo(new File(webPath+filePath+fileName));

            //解析
            List<ClaimVoucherInfo> list =  excel_to_claimVoucherInfo(new File(webPath+filePath+fileName));

            //开始 上传 数据库
            for(ClaimVoucherInfo info:list) {
                claimVoucherBiz.save(info.getClaimVoucher(),info.getItems());
            }

            //删除用过的文件
            FileUtil.deleteFile(webPath+filePath+fileName);
        }
        result.put("success", true);
        result.put("msg", "导入成功");
        return result;
    }

    private List<ClaimVoucherInfo> excel_to_claimVoucherInfo(File userUploadFile) throws ParseException {
        List<ClaimVoucherInfo> list = new ArrayList<>();
        ClaimVoucherInfo claimVoucherInfo = null;
        ClaimVoucherItem claimVoucherItem = null;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(userUploadFile));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            //获取第一个sheet页
            HSSFSheet sheet = wb.getSheetAt(0);
            if(sheet!=null){
                //从第二行开始导入数据
                for(int rowNum =1;rowNum<=sheet.getLastRowNum();rowNum++){
                    HSSFRow row = sheet.getRow(rowNum);
                    if(row==null){
                        continue;
                    }
                    if (row.getCell(0)!=null && !row.getCell(0).toString().isEmpty()){
                        claimVoucherInfo  = new ClaimVoucherInfo();
                        claimVoucherInfo.setClaimVoucher(new ClaimVoucher());
                        claimVoucherInfo.setItems(new ArrayList<ClaimVoucherItem>());
                        list.add(claimVoucherInfo);
                        //去掉创建者工号中的 .0  因为如果全是数字后面有.0
                        claimVoucherInfo.getClaimVoucher().setCreateSn(Integer.toString((int)(row.getCell(0).getNumericCellValue())));
                        claimVoucherInfo.getClaimVoucher().setCause(row.getCell(1).toString());
                        claimVoucherInfo.getClaimVoucher().setTotalAmount(row.getCell(2).getNumericCellValue());
                    }
                    claimVoucherItem = new ClaimVoucherItem();
                    claimVoucherItem.setItem(row.getCell(3).toString());
                    claimVoucherItem.setAmount(row.getCell(4).getNumericCellValue());
                    if (!row.getCell(5).toString().isEmpty() && row.getCell(5).toString() != null) {
                        claimVoucherItem.setComment(row.getCell(5).toString());
                    }else {
                        claimVoucherItem.setComment("无");
                    }
                    claimVoucherInfo.getItems().add(claimVoucherItem);
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    }
