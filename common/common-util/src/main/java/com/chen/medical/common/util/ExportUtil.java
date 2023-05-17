package com.chen.medical.common.util;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  导出工具类
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-17
 */
public class ExportUtil {

    private ExportUtil() {

    }

    /**
     * 使用 EasyExcel 进行 Excel 数据导出
     *
     * @param list 需要导出的数据列表
     * @param response HttpServletResponse 响应对象，用于设置响应头信息
     * @param fileName 文件名前缀，需要自行拼接上日期后缀（年月日时分秒），最终格式为：#{fileName}_yyyyMMddHHmmss.xlsx
     * @param clazz 数据实体类，例如 List<DictExportDTO>
     */
    public static <T> void exportExcel(List<T> list, HttpServletResponse response, String fileName, Class clazz)
            throws IOException {
        // 文件名
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        fileName = fileName + "_" + formattedDate + ".xlsx";

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        // 使用 EasyExcel 进行导出
        EasyExcel.write(response.getOutputStream(), clazz).sheet("Sheet1").doWrite(list);
    }
}
