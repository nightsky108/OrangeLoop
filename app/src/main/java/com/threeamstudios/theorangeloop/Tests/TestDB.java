package com.threeamstudios.theorangeloop.Tests;

import android.test.AndroidTestCase;
import com.threeamstudios.theorangeloop.*;

import java.util.ArrayList;

/**
 * Created by Charles on 4/28/2016.
 */
public class TestDB extends AndroidTestCase {

    public void testOrganizationSetters() throws Throwable {
        Organization org = new Organization();
        org.setOrgSize(5);
        org.setOrgName("Test Organization");
        ArrayList<Member> orgMembers = new ArrayList<Member>();
        orgMembers.add(new Member("Fred"));
        org.setOrgMembers(orgMembers);

        assertEquals(new Integer(5), org.getOrgSize());
        assertEquals("Test Organization", org.getOrgName());
        assertEquals("Fred", org.getOrgMembers().get(0).getName());

    }

    public void testMemberSetters() throws Throwable {
        Member mem = new Member();
        mem.setName("Kevin");
        ArrayList<Organization> orgList = new ArrayList<Organization>();

        orgList.add(new Organization("TesPA"));
        orgList.add(new Organization("Lions"));
        mem.setOrganizationList(orgList);

        assertEquals("Kevin", mem.getName());
        assertEquals("Lions", mem.getOrganizationList().get(1).getOrgName());
        assertEquals("TesPA", mem.getOrganizationList().get(0).getOrgName());
        assertEquals(new Integer(0), mem.getOrganizationList().get(0).getOrgSize());

    }

    public void testbestOrgOfMem() throws Throwable {
      Member mem = new Member();
      mem.setName("Charles");
      ArrayList<Organization> orgList = new ArrayList<Organization>();
      orgList.add(new Organization("TesPA"));
      orgList.add(new Organization("FSA"));
      orgList.add(new Organization("Lions"));
      orgList.add(new Organization("EE461L"));
      mem.setOrganizationList(orgList);
      assertEquals("EE461L", MemberController.bestOrgOfMem(mem));
    }

    // public void testBestOrganization() throws Throwable {
       /* public void testBestOrganization() throws Throwable {
        >>>>>>> origin/master
            MemberHomePage.organizationArrayList.add(new Organization("TesPA", 86));
            MemberHomePage.organizationArrayList.add(new Organization("Lions", 142));
            MemberHomePage.organizationArrayList.add(new Organization("EE461L", 81));
            MemberHomePage.organizationArrayList.add(new Organization("FSA", 120));
            MemberHomePage.organizationArrayList.add(new Organization("VSA", 165));
            MemberHomePage.organizationArrayList.add(new Organization("CSA", 103));
            MemberHomePage.organizationArrayList.add(new Organization("TSA", 92));
            assertEquals("VSA", OrganizationController.bestOrganization());
        }
        public void testMakeOrganization() {
            DatabaseHandler handler = new DatabaseHandler(this.getContext());

            handler.insertOrganization("FSA", "Filipino Students Association", "0");
            System.out.println(handler.getAllOrganization().get(0));
        }
        }*/
    // }
}
