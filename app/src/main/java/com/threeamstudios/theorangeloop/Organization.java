package com.threeamstudios.theorangeloop;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Josh M on 3/25/2016.
 */
public class Organization {

    // Fields Regarding organization
    private String orgName;
    private Integer orgSize;
    private String orgDesc;
    private String imageURL;
    private Integer id;

    // Array of members pertaining to said organization
    private ArrayList<Member> orgMembers;

    public Organization(){
        this.orgName = null;
        orgSize = 0;
        orgMembers = null;
    }

    public Organization(String orgName){
        this.orgName = orgName;
        this.orgSize = 0;
        this.orgMembers = null;
    }

    public Organization(String name, String desc , String img_URL){
        this.orgName = name;
        this.orgSize = 0;
        this.orgDesc = desc;
        this.imageURL = img_URL;
    }

    // Additional getters and setters which calls OrganizationController
    public String getOrgName() {
        return orgName;
    }

    public Integer getOrgSize() {
        return orgSize;
    }

    public ArrayList<Member> getOrgMembers() {
        return orgMembers;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgSize(Integer orgSize) {
        this.orgSize = orgSize;
    }

    public void setOrgMembers(ArrayList<Member> orgMembers) {
        this.orgMembers = orgMembers;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public String getImageURL() {
        return imageURL;
    }

    // Add/Get organization members from list
    public void addMemberToOrg(Member member) {
        ArrayList<Member> memDB = getOrgMembers();
        memDB.add(member);
        orgSize++;
    }

    public void getMemberFromOrg(Member member) {
        // Array might be empty
        try {
            ArrayList<Member> memDB = getOrgMembers();
            int memindex = memDB.indexOf(member);
            memDB.get(memindex);
            orgSize--;
        }
        catch(Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
