package org.gorczyca.pum.project4;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@SuppressLint("SetJavaScriptEnabled")
public class ContactBookActivity extends AppCompatActivity {

    private WebView webViewContactBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_4_contact_book);
        setTitle(R.string.contact_book);
        bindIds();
        setUpWebView();
    }

    private void bindIds() {
        webViewContactBook = findViewById(R.id.web_contact_book);
    }

    private void setUpWebView() {
        webViewContactBook.getSettings().setJavaScriptEnabled(true);
        webViewContactBook.getSettings().setDomStorageEnabled(true);
        webViewContactBook.addJavascriptInterface(new JavaScriptContactsService(this), "JavaScriptService");
        webViewContactBook.loadUrl("file:///android_asset/contacts.html");
    }

    public List<ContactItem> getContactsList() {
        List<ContactItem> contactItemList = new ArrayList<>();
        String[] cursorProjection = new String[]{ContactsContract.RawContacts.CONTACT_ID, ContactsContract.RawContacts.DELETED};
        Cursor rawContactsCursor = managedQuery(ContactsContract.RawContacts.CONTENT_URI, cursorProjection, null, null, null);
        int contactIdColumnIndex = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID);
        int deletedColumnIndex = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.DELETED);

        if (rawContactsCursor.moveToFirst()) {
            while (!rawContactsCursor.isAfterLast()) {
                int contactId = rawContactsCursor.getInt(contactIdColumnIndex);
                boolean deleted = (rawContactsCursor.getInt(deletedColumnIndex) == 1);

                if (!deleted) {
                    String photo = getPhoto(contactId) != null ? getPhoto(contactId).toString() : "";
                    ContactItem contactInfo = new ContactItem(contactId, getName(contactId), getEmail(contactId),
                            getAddress(contactId),  photo , getPhoneNumber(contactId), false);
                    contactItemList.add(contactInfo);
                }
                rawContactsCursor.moveToNext();
            }
        }
        rawContactsCursor.close();

        return contactItemList;
    }


    private String getName(int contactId) {
        String name = "";
        final String[] cursorProjection = new String[] { ContactsContract.Contacts.DISPLAY_NAME };

        final Cursor contact = managedQuery(ContactsContract.Contacts.CONTENT_URI, cursorProjection, ContactsContract.Contacts._ID + "=?", new String[] { String.valueOf(contactId) }, null);

        if (contact.moveToFirst()) {
            name = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contact.close();
        }
        contact.close();
        return name;

    }

    private String getEmail(int contactId) {
        StringBuilder emailStr = new StringBuilder();
        final String[] cursorProjection = new String[] { ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Email.TYPE };

        final Cursor email = managedQuery(ContactsContract.CommonDataKinds.Email.CONTENT_URI, cursorProjection, ContactsContract.Data.CONTACT_ID + "=?", new String[] { String.valueOf(contactId) }, null);

        if (email.moveToFirst()) {
            final int contactEmailColumnIndex = email.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

            while (!email.isAfterLast()) {
                emailStr.append(email.getString(contactEmailColumnIndex)).append(";");
                email.moveToNext();
            }
        }
        email.close();
        return emailStr.toString();

    }

    private Bitmap getPhoto(int contactId) {
        Bitmap photo = null;
        final String[] cursorProjection = new String[] { ContactsContract.Contacts.PHOTO_ID };

        final Cursor contact = managedQuery(ContactsContract.Contacts.CONTENT_URI, cursorProjection, ContactsContract.Contacts._ID + "=?", new String[] { String.valueOf(contactId) }, null);

        if (contact.moveToFirst()) {
            final String photoId = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
            if (photoId != null) {
                photo = getBitmap(photoId);
            } else {
                photo = null;
            }
        }
        contact.close();

        return photo;
    }

    private Bitmap getBitmap(String photoId) {
        final Cursor photoCursor = managedQuery(ContactsContract.Data.CONTENT_URI, new String[] { ContactsContract.CommonDataKinds.Photo.PHOTO }, ContactsContract.Data._ID + "=?", new String[] { photoId }, null);

        final Bitmap photoBitmap;
        if (photoCursor.moveToFirst()) {
            byte[] photoBlob = photoCursor.getBlob(photoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
            photoBitmap = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
        } else {
            photoBitmap = null;
        }
        photoCursor.close();
        return photoBitmap;
    }

    private String getAddress(int contactId) {
        String address = "";
        String selection = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(contactId), ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE };

        Cursor addressCursor = managedQuery(ContactsContract.Data.CONTENT_URI, null, selection, selectionArgs, null);

        if (addressCursor.moveToFirst()) {
            address = addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
        }
        addressCursor.close();
        return address;
    }

    private String getPhoneNumber(int contactId) {

        StringBuilder phoneNumber = new StringBuilder();
        final String[] cursorProjection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE, };
        final Cursor phoneCursor = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, cursorProjection, ContactsContract.Data.CONTACT_ID + "=?", new String[] { String.valueOf(contactId) }, null);

        if (phoneCursor.moveToFirst()) {
            final int contactNumberColumnIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

            while (!phoneCursor.isAfterLast()) {
                phoneNumber.append(phoneCursor.getString(contactNumberColumnIndex)).append(";");
                phoneCursor.moveToNext();
            }

        }
        phoneCursor.close();
        return phoneNumber.toString();
    }
}
