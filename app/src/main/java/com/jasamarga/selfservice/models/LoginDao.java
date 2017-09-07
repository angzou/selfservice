package com.jasamarga.selfservice.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apridosandyasa on 12/28/16.
 */

public class LoginDao implements Serializable {

    @SerializedName("Id")
    private int Id;
    @SerializedName("ASSIGNMENT_NUMBER")
    private String assignment_number;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("LAST_NAME")
    private String last_name;
    @SerializedName("api_token")
    private String api_token;
    @SerializedName("password")
    private String password;
    @SerializedName("SEX")
    private String sex;
    @SerializedName("AGAMA")
    private String agama;
    @SerializedName("TOWN_OF_BIRTH")
    private String town_of_birth;
    @SerializedName("DATE_OF_BIRTH")
    private String date_of_birth;
    @SerializedName("ALAMAT")
    private String alamat;
    @SerializedName("TEL_NUMBER_1")
    private String tel_number_1;
    @SerializedName("TEL_NUMBER_2")
    private String tel_number_2;
    @SerializedName("EMAIL_ADDRESS")
    private String email_address;
    @SerializedName("GRADE")
    private String grade;
    @SerializedName("POSITION_NAME")
    private String position_name;
    @SerializedName("PARENT_ASSIGNMENT_NUMBER")
    private String parent_assignment_number;
    @SerializedName("LOCATION_ADDRESS")
    private String location_address;
    @SerializedName("LOCATION_NAME")
    private String location_name;
    @SerializedName("EMPLOYMENT_CATEGORY")
    private String employment_category;
    @SerializedName("UNIT_KERJA")
    private String unit_kerja;
    @SerializedName("urlfoto")
    private String urlfoto;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNpp() {
        return assignment_number;
    }

    public void setNpp(String assignment_number) {
        this.assignment_number = assignment_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return last_name;
    }

    public void setNama(String last_name) {
        this.last_name = last_name;
    }

    public String getJenis_kelamin() {
        return sex;
    }

    public void setJenis_kelamin(String sex) {
        this.sex = sex;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getTempatlahir() {
        return town_of_birth;
    }

    public void setTempatlahir(String town_of_birth) {
        this.town_of_birth = town_of_birth;
    }

    public String getTanggallahir() {
        return date_of_birth;
    }

    public void setTanggallahir(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp1() {
        return tel_number_1;
    }

    public void setTelp1(String tel_number_1) {
        this.tel_number_1 = tel_number_1;
    }

    public String getTelp2() {
        return tel_number_2;
    }

    public void setTelp2(String tel_number_2) {
        this.tel_number_2 = tel_number_2;
    }

    public String getEmail() {
        return email_address;
    }

    public void setEmail(String email_address) {
        this.email_address = email_address;
    }

    public String getGolongan() {
        return grade;
    }

    public void setGolongan(String grade) {
        this.grade = grade;
    }

    public String getJabatan() {
        return position_name;
    }

    public void setJabatan(String position_name) {
        this.position_name = position_name;
    }

    public String getNpp_atasan() {
        return parent_assignment_number;
    }

    public void setNpp_atasan(String parent_assignment_number) {
        this.parent_assignment_number = parent_assignment_number;
    }

    public String getKantor_alamat() {
        return location_address;
    }

    public void setKantor_alamat(String location_address) {
        this.location_address = location_address;
    }

    public String getKantor_desc() {
        return location_name;
    }

    public void setKantor_desc(String location_name) {
        this.location_name = location_name;
    }

    public String getStatus_desc() {
        return employment_category;
    }

    public void setStatus_desc(String employment_category) {
        this.employment_category = employment_category;
    }

    public String getUnit_desc() {
        return unit_kerja;
    }

    public void setUnit_desc(String unit_kerja) {
        this.unit_kerja = unit_kerja;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

}

