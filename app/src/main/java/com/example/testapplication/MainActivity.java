package com.example.testapplication;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.impl.BoxingUcrop;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bilibili.boxing.impl.BoxingGlideLoader;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private String[] permissions={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String EXTRA_MESSAGE="com.example.testapplication.MESSAGE";
    public static final String TAG="Connection Error";
    public static final MediaType JSON=MediaType.get("application/json; charset=utf-8");
    TextView textView2;
    EditText editAccount;
    EditText editPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2=(TextView)findViewById(R.id.textView2);
        editAccount=(EditText)findViewById(R.id.editAccount);
        editPwd=(EditText)findViewById(R.id.editPwd);
        getPermission();
    }
    public void SendMessage(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://210.42.105.207/testconnection")
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);

                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }

        }).start();

        //Intent intent =new Intent(this,DisplayPicsActivity.class);
        //EditText editText=(EditText) findViewById(R.id.editPwd);

        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE,message);
       // startActivity(intent);
    }
    public void Login(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Gson gson=new Gson();
                    LoginBody body=new LoginBody(editAccount.getText().toString(),"",editPwd.getText().toString());
                    RequestBody requestBody=RequestBody.create(JSON,gson.toJson(body));
                    Request request=new Request.Builder()
                            .url("http://210.42.105.207/login")
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"You clicked Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"You clicked Remove",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
    public void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView2.setText(response);
            }
        });
    }
    public  void openAlbum(View view){
        IBoxingMediaLoader loader=new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
        Intent intent =new Intent(MainActivity.this,DisplayPicsActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode,int reslutCode,Intent data){
        if(requestCode==2 && reslutCode==RESULT_OK){
            Log.d(TAG, "onActivityResult: "+data.getData());
        }
    }
    private void getPermission(){
        if(EasyPermissions.hasPermissions(this,permissions)){
            //已经打开权限
            Toast.makeText(this,"已经申请相关权限",Toast.LENGTH_SHORT).show();
        }else {
            //没有权限，申请权限
            EasyPermissions.requestPermissions(this,"需要申请相册权限",1,permissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this,"权限获取成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this,"请同意相关权限",Toast.LENGTH_SHORT).show();
    }
}
