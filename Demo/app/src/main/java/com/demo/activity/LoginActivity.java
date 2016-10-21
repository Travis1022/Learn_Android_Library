package com.demo.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.R;
import com.demo.db.DaoMaster;
import com.demo.db.DaoSession;
import com.demo.db.UserDao;
import com.demo.db.entity.User;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 使用greenDao实现离线登录
 * 登录activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.et_username)
    EditText mEtUsername;
    @InjectView(R.id.et_password)
    EditText mEtPassword;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private UserDao mUserDao;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //using butter knife
        ButterKnife.inject(this);
        setDataBase();
        mUserDao = this.getDaoSession().getUserDao();
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                // greenDao 查询
                List<User> users = mUserDao.loadAll();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserNumber().equals(mEtUsername.getText().toString())
                            && users.get(i).getUserPassword().equals(mEtPassword.getText().toString())) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, IndexActivity.class));
                    }
                }
                break;
            case R.id.btn_register:
                // greenDao 插入
                mUser = new User((long) 2, "100", "100");
                mUserDao.insertOrReplace(mUser);
                Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void setDataBase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "demo-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }


    private DaoSession getDaoSession() {
        return mDaoSession;
    }

    private SQLiteDatabase getDb() {
        return db;
    }

}
