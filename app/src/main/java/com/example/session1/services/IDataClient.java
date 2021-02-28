package com.example.session1.services;

import com.example.session1.constants.AppConstants;
import com.example.session1.models.AccountableParty;
import com.example.session1.models.AssetCatalogues;
import com.example.session1.models.AssetGroups;
import com.example.session1.models.AssetTransferLogs;
import com.example.session1.models.DepartmentLocations;
import com.example.session1.models.Departments;
import com.example.session1.models.Locations;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IDataClient {

    @GET("get/getDepartments.php")
    Call<List<Departments>> getDepartments();

    @GET(AppConstants.API_READ_ASSETGROUPS)
    Call<List<AssetGroups>> getAssetGroups();

//    @GET("get/getAssetCatalogues.php")
//    Call<List<AssetCatalogues>> getAssetCatalogues();
//
//    @GET("get/getLocations.php")
//    Call<List<Locations>> getLocations();
//
//    @GET("get/getEmployees.php")
//    Call<List<AccountableParty>> getAccountableParty();
//
//    @GET("get/getAssetTransferLogs.php")
//    Call<List<AssetTransferLogs>> getAssetTransferLogs();
//
//    @GET("search.php")
//    Call<List<AssetCatalogues>> getSearch(@Query("assetname") String assetname);
//
//    @GET("get/getDepartmentLocationID.php")
//    Call<List<DepartmentLocations>> getDepartmentLocationID(@Query("departmentid") long departmentid,
//                                                            @Query("locationid") long locationid);
//
//    @FormUrlEncoded
//    @POST("set/setAssetCatalogues.php")
//    Call<List<AssetCatalogues>> setAssetCatalogues(@Field("assetsn") String assetsn,
//                                                   @Field("assetname") String assetname,
//                                                   @Field("departmentlocationid") long dlid,
//                                                   @Field("employeeid") long eid,
//                                                   @Field("assetgroupid") long agid,
//                                                   @Field("description") String description,
//                                                   @Field("warrantydate") String warrantydate);

    @FormUrlEncoded
    @POST("uploadimage.php")
    Call<String> uploadAssetPhoto(@Part MultipartBody.Part part);

    @FormUrlEncoded
    @POST("set/setAssetPhotos.php")
    Call<String> setAssetPhotos(@Field("assetid") long assetid,
                                @Field("assetphoto") String photo);
}
