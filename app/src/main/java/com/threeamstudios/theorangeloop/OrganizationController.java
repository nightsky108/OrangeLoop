package com.threeamstudios.theorangeloop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import com.threeamstudios.theorangeloop.Params;

/**
 * Created by Josh M on 3/25/2016.
 */
public class OrganizationController {

    // Replicate actual server by filling MySQL Database for our SQLite Database
    public static void mimicInsertDataFromServer(DatabaseHandler handler) {

        // Clear old DB and instantiante new
        handler.resetDB(handler);
        handler.onCreate(handler.getReadableDatabase());

        // Name, desc, img_URL
        Organization vsa = new Organization("VSA", "Flag Football Team", "https://i.imgur.com/JgT66Ws.jpg");
        Organization fsa = new Organization("FSA", "Modern Dance", "https://i.imgur.com/PjkjcXX.jpg");
        Organization epic = new Organization("EPIC", "Large Group", "https://i.imgur.com/MTWgjvx.jpg");
        Organization ieee = new Organization("IEEE", "Intel Networking", "http://i.imgur.com/TKtzoP3.jpg");
        handler.insertOrganization(vsa);
        handler.insertOrganization(fsa);
        handler.insertOrganization(epic);
        handler.insertOrganization(ieee);
    }

    public static Boolean popularClub(Organization organization){
        if(organization.getOrgSize() > 20){
            return true;
        }else {
            return false;
        }
    }

    public static String bestOrganization(){
        Organization bestOrg = new Organization();
        bestOrg.setOrgSize(-1);
        if(Params.ORGANIZATIONLIST.size()== 0){
            return null;
        }for(int i = 0; i < Params.ORGANIZATIONLIST.size(); i +=1){
            if(Params.ORGANIZATIONLIST.get(i).getOrgSize() > bestOrg.getOrgSize()){
                bestOrg = Params.ORGANIZATIONLIST.get(i);
            }
        }
        return bestOrg.getOrgName();
    }
}
