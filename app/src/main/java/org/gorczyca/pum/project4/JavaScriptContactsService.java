package org.gorczyca.pum.project4;

import android.content.Context;
import android.webkit.JavascriptInterface;
import java.util.List;

/**
 * Created by: Radosław Gorczyca
 * 27.06.2020 18:48
 */
class JavaScriptContactsService {

    private Context context;

    public JavaScriptContactsService(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public String showContacts() {
        StringBuilder innerHtml = new StringBuilder();
        List<ContactItem> contacts;
        if(context instanceof ContactBookActivity) {
            contacts = ((ContactBookActivity) context).getContactsList();
        } else return null;

        for (int i = 0; i < contacts.size(); i++) {
            innerHtml.append("<div class='container'>\n" +
                    "    <h2>Kontakty</h2>\n" +
                    "    <p>Wyszukaj kontakt:</p>\n" +
                    "    <input class='form-control' id='myInput' type='text' placeholder='Wyszukaj..'>\n" +
                    "    <br>\n" +
                    "    <table id='contactTable' class='table table-condensed' style='border-collapse:collapse;'><thead><tr><th>Imie</th><th>Telefon</th></tr></thead><tbody id='myTable'></tbody>")
                    .append("<tr data-toggle='collapse' data-target='#demo")
                    .append(i).append("' class='accordion-toggle'>")
                    .append("<td>")
                    .append(contacts.get(i).getName()).
                    append("</td>").append("<td>")
                    .append(contacts.get(i).getPhone())
                    .append("</td>").append("</tr>")
                    .append("<tr >").append("<td colspan='6' class='hiddenRow'>")
                    .append("<div class='accordian-body collapse' id='demo")
                    .append(i).append("> ").append("Email: ")
                    .append(contacts.get(i).getEmail()).append("  ").
                    append("Address: ").append(contacts.get(i).getAddress()).
                    append("  ").append("Photo: ").append(contacts.get(i).getPhoto())
                    .append("  ").append("</div> </td>").append("</tr></table>\n" +
                    "</div>");
        }
        return innerHtml.toString();
    }
}
