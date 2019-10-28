package com.github.shrimpbook.items;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Lee on 10/25/2019.
 */

public class ViewItem {

    private ParseFile file;
    private String shrimpType;
    private String tankSize;
    private String soilType;
    private String pH;
    private String GH;
    private String KH;
    private String temp;
    private String TDS;

    private String viewObjectId;

    public ViewItem (ParseObject object) {
        this.file = (ParseFile) object.get("image");
        this.shrimpType = (String) object.get("shrimpType");
        this.tankSize = (String) object.get("tankSize");

        this.soilType = (String) object.get("soilType");
        this.pH = (String) object.get("pH");

        this.GH = (String) object.get("GH");
        this.KH = (String) object.get("KH");
        this.viewObjectId = (String) object.getObjectId();

        this.temp = (String) object.get("temp");
        this.TDS = (String) object.get("TDS");
    }

    public String getViewObjectId () {
        return viewObjectId;
    }

    public ParseFile getFile() {
        return file;
    }

    public String getShrimpType () {
        return shrimpType;
    }

    public String getTankSize () {
        return tankSize;
    }

    public String getSoilType () {
        return soilType;
    }

    public String getpH () {
        return pH;
    }

    public String getGH () {
        return GH;
    }

    public String getKH () {
        return KH;
    }

    public String getTDS () {
        return TDS;
    }

    public String getTemp () {
        return temp;
    }

}
