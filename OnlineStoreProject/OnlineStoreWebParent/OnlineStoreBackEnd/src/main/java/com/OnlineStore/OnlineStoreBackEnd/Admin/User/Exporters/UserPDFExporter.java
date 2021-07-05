package com.OnlineStore.OnlineStoreBackEnd.Admin.User.Exporters;

import com.OnlineStore.OnlineStoreCommon.Entity.Role;
import com.OnlineStore.OnlineStoreCommon.Entity.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class UserPDFExporter {


    public void export(List<User> listUsers, HttpServletResponse response) throws FileNotFoundException {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        response.setContentType("application/pdf");

        document.open();

        PdfPTable table = new PdfPTable(6);
        String[] csvHeaders = {"User ID", "E-mail", "First Name", "Last Name", "Roles", "Enabled"};
        addTableHeader(table, csvHeaders);
        addRows(table, listUsers);

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();


    }

    private void addTableHeader(PdfPTable table, String[] csvHeaders) {
        Stream.of(csvHeaders)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<User> listUsers) {

        for (User user : listUsers) {

            String userID = String.valueOf(user.getId());
            table.addCell(userID);
            table.addCell(user.getEmail());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());

            var s = user.getRoles();
            int n = s.size();
            String arr[] = new String[n];

            int i = 0;
            for (Role x : s)
                arr[i++] = x.getName();
            var arrayOfRoles = Arrays.toString(arr);

            table.addCell(arrayOfRoles);

            var enabledVal = String.valueOf(user.getEnabled());

            table.addCell(enabledVal);


        }


    }

}
