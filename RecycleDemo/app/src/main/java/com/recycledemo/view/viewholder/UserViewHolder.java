package com.recycledemo.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.recycledemo.R;
import com.recycledemo.model.User;
import com.sunnybear.library.recycler.BasicViewHolder;

import butterknife.Bind;

/**
 * Created by Travis on 2016/10/22.
 */
public class UserViewHolder extends BasicViewHolder<User> {
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_user1)
    TextView tvUser1;
    @Bind(R.id.tv_user2)
    TextView tvUser2;
    @Bind(R.id.tv_user3)
    TextView tvUser3;
    @Bind(R.id.tv_user4)
    TextView tvUser4;
    @Bind(R.id.tv_user5)
    TextView tvUser5;
    @Bind(R.id.tv_user6)
    TextView tvUser6;


    // 如果有针对单个TextView的点击事件，则写入此处

    public UserViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindItem(User user, int position) {
        tvUser.setText(user.getUserName());
        tvUser1.setText(user.getUserName1());
        tvUser2.setText(user.getUserName2());
        tvUser3.setText(user.getUserName3());
        tvUser4.setText(user.getUserName4());
        tvUser5.setText(user.getUserName5());
        tvUser6.setText(user.getUserName6());

    }
}
