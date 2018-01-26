package com.gs.gscartoon.zhijia.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gs.gscartoon.R;
import com.gs.gscartoon.utils.AppConstants;
import com.gs.gscartoon.utils.StatusBarUtil;
import com.gs.gscartoon.utils.ToolbarUtil;
import com.gs.gscartoon.zhijia.ZhiJiaBrowseContract;
import com.gs.gscartoon.zhijia.adapter.ZhiJiaBrowseRecyclerAdapter;
import com.gs.gscartoon.zhijia.model.ZhiJiaBrowseModel;
import com.gs.gscartoon.zhijia.presenter.ZhiJiaBrowsePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ZhiJiaBrowseActivity extends AppCompatActivity implements ZhiJiaBrowseContract.View,
        View.OnClickListener{
    private static final String TAG = "ZhiJiaBrowseActivity";

    @BindView(R.id.tb_zhi_jia_browse)
    Toolbar tbToolbar;
    @BindView(R.id.rv_zhi_jia_browse)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_toolbar_zhi_jia_browse_title)
    TextView tvTitle;

    private ZhiJiaBrowseContract.Presenter mPresenter;
    private ZhiJiaBrowseRecyclerAdapter mRecyclerAdapter;
    private Unbinder unbinder;
    private int mComicsId;//某一话漫画Id
    private int mTopicId;//漫画Id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_jia_browse);

        StatusBarUtil.enableTranslucentStatusBar(this);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        ToolbarUtil.initToolbar(this, tbToolbar);

        mComicsId = getIntent().getIntExtra(AppConstants.COMICS_ID, 0);
        mTopicId = getIntent().getIntExtra(AppConstants.TOPIC_ID, 0);

        // Create the presenter
        new ZhiJiaBrowsePresenter(
                new ZhiJiaBrowseModel(getApplicationContext()), this);

        mRecyclerAdapter = new ZhiJiaBrowseRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        mComicsId = getIntent().getIntExtra(AppConstants.COMICS_ID, 0);
        mTopicId = getIntent().getIntExtra(AppConstants.TOPIC_ID, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.refreshData(mTopicId, mComicsId);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(ZhiJiaBrowseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showRefreshData(List<String> mData) {
        mRecyclerAdapter.clear();
        mRecyclerAdapter.addItems(mData);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshDataFailure() {

    }

    @Override
    public void setTitle(String title) {
        if(tvTitle != null){
            tvTitle.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_toolbar_zhi_jia_browse_title:

                break;
            default:
                break;
        }
    }
}