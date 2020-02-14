package com.threeamstudios.theorangeloop;

import java.util.ArrayList;

/**
 * Created by Josh M on 3/25/2016.
 */
public class Member {

    // Fields regarding Members.
    private String memberName;
    private ArrayList<Organization> organizationList;

    public Member(){
        this.memberName = null;
        this.organizationList = null;
    }

    public Member(String name){
        this.memberName = name;
    }

    public Member(String memberName, ArrayList<Organization> organizationList){
        this.memberName = memberName;
        this.organizationList = organizationList;
    }

    // Getters and setters
    public String getName() {
        return this.memberName;
    }

    public ArrayList<Organization> getOrganizationList() {
        return this.organizationList;
    }

    public void setName(String name){
        this.memberName = name;
    }

    public void setOrganizationList(ArrayList<Organization> organizationList) {
        this.organizationList = organizationList;
    }

}
