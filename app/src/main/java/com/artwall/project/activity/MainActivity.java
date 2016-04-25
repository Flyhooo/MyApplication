package com.artwall.project.activity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.activity.send.SendChildrenActivity;
import com.artwall.project.activity.send.SendPhotographyActivity;
import com.artwall.project.activity.send.SendQuestionActivity;
import com.artwall.project.activity.send.SendTeachActivity;
import com.artwall.project.activity.send.SendYaopingActivity;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.config.Config;
import com.artwall.project.fragment.ChildrenFragment;
import com.artwall.project.fragment.MarketFragment;
import com.artwall.project.fragment.PaintFragment;
import com.artwall.project.fragment.PhotographyFragment;
import com.artwall.project.fragment.QuestionFragment;
import com.artwall.project.fragment.YaoPingFragment;
import com.artwall.project.manager.AppManager;
import com.artwall.project.util.DialogUtils;
import com.artwall.project.util.ImageLoaderUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;

    /**
     * 点击添加出现蒙版效果
     */
    private RelativeLayout cloud;

    /**
     * 0、绘画 1、大咖 2、儿童 3、话题 4、集市
     */
    private int type = 0;

    private PaintFragment paintFragment;
    private PhotographyFragment photographyFragment;
    private ChildrenFragment childrenFragment;
    private QuestionFragment questionFragment;
    private MarketFragment marketFragment;
    private YaoPingFragment yaoPingFragment;


    /**
     * 标题栏
     */
    private LinearLayout topLL;
    private TextView titleTV;
    private TextView yaopingTV;
    /**
     * 单独标题
     */
    private TextView titleTV_Only;

    /**
     * 个人页面
     */
    private View headerView;
    private ImageView userImg;
    private TextView userName, userIntroduc;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) findViewById(R.id.toolbars);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        cloud = (RelativeLayout) this.findViewById(R.id.main_cloud);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        topLL = (LinearLayout) this.findViewById(R.id.main_top_LL);
        titleTV = (TextView) this.findViewById(R.id.main_topTitle_title_TV);
        yaopingTV = (TextView) this.findViewById(R.id.main_topTitle_yaoping_TV);
        titleTV_Only = (TextView) this.findViewById(R.id.main_toptitle_only_TV);

        headerView = navigationView.getHeaderView(0);
        userImg = (ImageView) headerView.findViewById(R.id.main_navHeader_image);
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.isLogin) {
                    startActivity(new Intent(activity, UserInfoActivity.class));
                } else {
                    startActivity(new Intent(activity, LoginActivity.class));
                }
            }
        });
        userName = (TextView) headerView.findViewById(R.id.main_navHeader_username);
        userIntroduc = (TextView) headerView.findViewById(R.id.main_navHeader_introduc);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        Config.SCREEN_WIDTH = metric.widthPixels;     // 屏幕宽度（像素）
        Config.SCREEN_WIDTH = metric.heightPixels;   // 屏幕高度（像素）
    }

    @Override
    protected void initData() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fabOpened) {
                    openMenu(view);
                } else {
                    closeMenu(view);
                }
            }
        });

        //根据登录状态初始化个人资料
        if (App.isLogin) {
            userName.setText(App.userInfo.getNickname().toString());
            ImageLoaderUtil.loadImg(userImg, App.userInfo.getImg());
            if (null == App.userInfo.getIntroduction()) {
                userIntroduc.setText("我就是我，没有简介的我...");
            } else {
                userIntroduc.setText(App.userInfo.getIntroduction().toString());
            }
        } else {
            userIntroduc.setVisibility(View.GONE);
            userName.setText("点击登录");
            userImg.setImageResource(R.drawable.ic_menu_camera);
        }

        //初始化顶部按钮
        titleTV.setSelected(true);
        yaopingTV.setSelected(false);

        paintFragment = PaintFragment.getInstance();
        photographyFragment = PhotographyFragment.getInstance();
        childrenFragment = ChildrenFragment.getInstance();
        questionFragment = QuestionFragment.getInstance();
        marketFragment = MarketFragment.getInstance();
        yaoPingFragment = YaoPingFragment.getInstance();
        currentFragment = paintFragment;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_contains, paintFragment, "paint").addToBackStack(null).commit();

        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu(fab);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (cloud.getVisibility() == View.VISIBLE) {
            closeMenu(fab);
        } else {
            DialogUtils.showDialog(this, "", "确定退出应用？", "不了", "退出", null, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppManager.getAppManager().AppExit(activity);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_shopcar:

                break;

            case R.id.action_message:
                startActivity(new Intent(this, MessageActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            changeContent(0);
        } else if (id == R.id.nav_gallery) {
            changeContent(1);
        } else if (id == R.id.nav_slideshow) {
            changeContent(2);
        } else if (id == R.id.nav_manage) {
            changeContent(3);
        } else if (id == R.id.nav_market) {
            changeContent(4);
        } else if (id == R.id.nav_userCenter) {
            startActivity(new Intent(activity, UserCenterActivity.class));

        } else if (id == R.id.nav_account) {
            startActivity(new Intent(activity, AccountActivity.class));

        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(activity, SettingActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /***
     * 高效切换fragment
     *
     * @param fragment1
     * @param tag
     */
    public void change(Fragment fragment1, String tag) {

        Fragment fragment = fragmentManager.findFragmentByTag(tag);//判断此Fragment是否已经被add //若不曾被add
        if (fragment == null) {
            fragmentManager.beginTransaction().hide(currentFragment).add(R.id.main_contains, fragment1, tag).addToBackStack(null).commit();
            currentFragment = fragment1;
        } else if (fragment1.isHidden()) {  //如果已经被add并且处于隐藏状态     //将当前Fragment隐藏并显示fragment1
            fragmentManager.beginTransaction().hide(currentFragment).show(fragment1).addToBackStack(null).commit();
            currentFragment = fragment1;
        }
    }

    /**
     * 改变主界面显示
     *
     * @param index 0、绘画 1、大咖 2、儿童 3、话题 4、集市
     */
    private void changeContent(int index) {
        if (index == type) {
            return;
        }
        type = index;
        switch (index) {
            case 0:
                if (topLL.getVisibility() == View.GONE) {
                    titleTV_Only.setVisibility(View.GONE);
                    topLL.setVisibility(View.VISIBLE);
                }
                if (!titleTV.isSelected()) {
                    titleTV.setSelected(true);
                    yaopingTV.setSelected(false);
                }
                titleTV.setText("绘画");
                yaopingTV.setText("邀评");
                change(paintFragment, "paint");
                break;
            case 1:
                if (titleTV_Only.getVisibility() == View.GONE) {
                    titleTV_Only.setVisibility(View.VISIBLE);
                    topLL.setVisibility(View.GONE);
                }
                titleTV_Only.setText("大咖");
                change(photographyFragment, "photo");
                break;
            case 2:
                if (topLL.getVisibility() == View.GONE) {
                    titleTV_Only.setVisibility(View.GONE);
                    topLL.setVisibility(View.VISIBLE);
                }
                if (!titleTV.isSelected()) {
                    titleTV.setSelected(true);
                    yaopingTV.setSelected(false);
                }
                titleTV.setText("儿童");
                yaopingTV.setText("创作");
                change(childrenFragment, "child");
                break;

            case 3:
                if (titleTV_Only.getVisibility() == View.GONE) {
                    titleTV_Only.setVisibility(View.VISIBLE);
                    topLL.setVisibility(View.GONE);
                }
                titleTV_Only.setText("话题");
                change(questionFragment, "question");
                break;
            case 4:
                if (titleTV_Only.getVisibility() == View.GONE) {
                    titleTV_Only.setVisibility(View.VISIBLE);
                    topLL.setVisibility(View.GONE);
                }
                titleTV_Only.setText("集市");
                change(marketFragment, "market");
                break;
        }
    }


    public void click(View view) {
        switch (view.getId()) {
            case R.id.main_topTitle_title_TV:
                if (!titleTV.isSelected()) {
                    titleTV.setSelected(true);
                    yaopingTV.setSelected(false);

                    if (type == 0) {
                        //书画
                        change(paintFragment, "paint");
                    } else if (type == 2) {
                        //儿童
                        change(childrenFragment, "child");
                    }
                }

                break;

            case R.id.main_topTitle_yaoping_TV:

                if (!yaopingTV.isSelected()) {
                    yaopingTV.setSelected(true);
                    titleTV.setSelected(false);
                    if (type == 0) {
                        //绘画邀评
                        change(yaoPingFragment, "yaoping");
                    } else if (type == 2) {
                        //儿童创作
                        change(yaoPingFragment, "yaoping");
                    }

                }
                break;

            case R.id.main_send_question_TV:
                startActivity(new Intent(activity, SendQuestionActivity.class));
                closeMenu(fab);
                break;

            case R.id.main_send_children_TV:
                startActivity(new Intent(activity, SendChildrenActivity.class));
                closeMenu(fab);
                break;
            case R.id.main_send_photography_TV:
                startActivity(new Intent(activity, SendPhotographyActivity.class));
                closeMenu(fab);
                break;

            case R.id.main_send_paintandwrite_TV:
                startActivity(new Intent(activity, SendTeachActivity.class));
                closeMenu(fab);
                break;
            case R.id.main_send_yaoping_TV:
                startActivity(new Intent(activity, SendYaopingActivity.class));
                closeMenu(fab);
                break;

        }
    }

    boolean fabOpened = false;

    /**
     * 打开发布选择界面
     *
     * @param view
     */
    private void openMenu(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, -155, -135);
        animator.setDuration(500);
        animator.start();
        cloud.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.7f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        cloud.startAnimation(alphaAnimation);
        fabOpened = true;
    }

    /**
     * 关闭选择发布界面
     *
     * @param view
     */
    private void closeMenu(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", -135, 20, 0);
        animator.setDuration(500);
        animator.start();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, 0);
        alphaAnimation.setDuration(500);
        cloud.startAnimation(alphaAnimation);
        cloud.setVisibility(View.GONE);
        fabOpened = false;
    }

}
