package com.threeamstudios.theorangeloop;
import com.threeamstudios.theorangeloop.Params;

import java.util.ArrayList;

/**
 * Created by Josh M on 3/25/2016.
 */
public class MemberController {

    /*public static String busyMember(){
        ArrayList<Member> AllMembers= new ArrayList<Member>();
        Member busyMember = new Member();
        for(int orgs = 0; orgs<Params.ORGANIZATIONLIST.size(); orgs+=1 ){
            for(int mem = 0; mem <Params.ORGANIZATIONLIST.get(orgs).getOrgSize(); mem +=1){
                if(AllMembers.contains(Params.ORGANIZATIONLIST.get(orgs).getOrgMembers().get(mem)) ){
                    AllMembers.add(((int)Params.ORGANIZATIONLIST.get(orgs).getOrgMembers().get(mem).getName()%6),Params.ORGANIZATIONLIST.get(orgs).getOrgMembers().get(mem));
                }
            }
        }

        return busyMember.getName();
    }*/
    public static String bestOrgOfMem(Member member){
        Organization tmp = new Organization();
        if(member.getOrganizationList().size()!= 0){
            tmp = member.getOrganizationList().get(0);
            for(int i = 1; i < member.getOrganizationList().size(); i +=1) {
                if(member.getOrganizationList().get(i).getOrgSize() > tmp.getOrgSize()){
                    tmp = member.getOrganizationList().get(i);
                }
            }
            return tmp.getOrgName();
        }
        else{
            return null;
        }
    }



}
