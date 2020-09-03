package com.ekenya.echama.ui.group;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.ekenya.echama.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * UNDER TESTING NOT YET USED IN PROD
 */
public class ContactList {

    public class Contact{

    }

    public static List<Member> getContacts(Activity context) {
       // if(null!=context.getValue()) {
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            String name;
            String phone = null;
            List<Member> contactList = new ArrayList<>();
            int i = 1;

            if (cur.getCount() > 0) {

                String image_uri = "";
                while (cur.moveToNext()) {
                    if (i == 1) {
                        cur.moveToFirst();
                        i++;
                    }
                    String id = cur.getString(cur
                            .getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    image_uri = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[]{id}, null);
                    if (pCur.moveToNext()) {
                        phone = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        System.out.println("Name: " + name + "phone: " + phone);
                        phone = phone.replaceAll("\\s+", "");
                    }
                    pCur.close();
                   // Contact contact = new Contact(name, image_uri, phone, false, Integer.parseInt(id));
                    Member member = new Member(name,phone);
                    contactList.add(member);
                }


                cur.close();
            }
            return contactList;
        //}

       // return null;
    }
}
