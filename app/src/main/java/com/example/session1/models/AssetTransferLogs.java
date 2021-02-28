package com.example.session1.models;

public class AssetTransferLogs {
    String transfer_date, old_dl, old_assetsn, new_dl, new_assetsn;

    public AssetTransferLogs(String transfer_date, String old_dl, String old_assetsn, String new_dl, String new_assetsn) {
        this.transfer_date = transfer_date;
        this.old_dl = old_dl;
        this.old_assetsn = old_assetsn;
        this.new_dl = new_dl;
        this.new_assetsn = new_assetsn;
    }

    public String getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    public String getOld_dl() {
        return old_dl;
    }

    public void setOld_dl(String old_dl) {
        this.old_dl = old_dl;
    }

    public String getOld_assetsn() {
        return old_assetsn;
    }

    public void setOld_assetsn(String old_assetsn) {
        this.old_assetsn = old_assetsn;
    }

    public String getNew_dl() {
        return new_dl;
    }

    public void setNew_dl(String new_dl) {
        this.new_dl = new_dl;
    }

    public String getNew_assetsn() {
        return new_assetsn;
    }

    public void setNew_assetsn(String new_assetsn) {
        this.new_assetsn = new_assetsn;
    }
}
