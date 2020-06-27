package org.gorczyca.pum.project4;

import androidx.annotation.NonNull;

/**
 * Created by: Radosław Gorczyca
 * 27.06.2020 18:50
 */
public class ContactItem {

    private int contactId;
    private String name;
    private String email;
    private String address;
    private String photo;
    private String phone;
    private boolean isChecked;

    public ContactItem(int contactId, String name, String email, String address, String photo, String phone, boolean isChecked) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.photo = photo;
        this.phone = phone;
        this.isChecked = isChecked;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @NonNull
    @Override
    public String toString() {
        return "{contactId=" + contactId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", photo='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                ", isChecked=" + isChecked + '}';
    }
}
