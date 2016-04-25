package com.artwall.project.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.activity.QuestionDetailActivity;
import com.artwall.project.adapter.QuestionAdapter;
import com.artwall.project.api.IMG;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.Question;
import com.artwall.project.widget.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class QuestionFragment extends BaseFragment {

    private static QuestionFragment fragment = null;

    public static QuestionFragment getInstance() {
        if (fragment == null) {
            return new QuestionFragment();
        } else {
            return fragment;
        }
    }

    private ArrayList<Question> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuestionAdapter adapter;

    @Override
    public int getMianLayout() {
        return R.layout.fragment_question;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.Question_recyclerview);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 5; i++) {
            Question question = new Question();
            switch (i) {
                case 0:
                    question.setUserName("如火如荼");
                    question.setImage(IMG.IMG5);
                    question.setTitle("在美国成为终身教授到底有多困难？终身教授大概是个什么水平？");
                    question.setContent("12.28修改。本来是给在美国的学生看的，很多地方习惯性地夹杂了一些英语，能改的尽量改过来，有的像MIT之类的我想不需要翻译吧？———————不一定，看什么学校。前面有人说过了，MIT这样的地方整个运行…");
                    break;
                case 1:
                    question.setUserName("射日弓");
                    question.setTitle("榫卯结构，为什么现在使用这么少？");
                    question.setContent("个深刻的家具设这个问题好想答呀！但是肿么全是建筑的标签，我一个做家具的都不好意思下手了。在回答问题前，首先要说两点。 基于@SunLau的影响力非常大，但是他有2点说死了，担心会影响知友的判断。成本：榫卯结构意味着家具不可拆卸，不能使用平板包装，必须整个家具打");
                    break;
                case 2:
                    question.setUserName("他夜静听雨");
                    question.setTitle("异形的来源是什么？《异形》第一部里的卵是谁留下来的？");
                    question.setContent("异形来源的扯淡。。。一直有人问什么是异形，为什么异形有这么多形态，又是章鱼又是三角脑袋的。我这里给不熟悉异形系列的同学补一下课。按照目前的一系列异形相关电影，我们知道异形系列里面有4个有机生物的种族。1、我们人类2、异形3、长相和穿着都和伏地…");
                    break;
                case 3:
                    question.setUserName("咯可分");
                    question.setImage(IMG.IMG3);
                    question.setTitle("与数学撇清关系的经济学是不是都在耍流氓？");
                    question.setContent("不请自来。作为一个前后学习经济/金融九年的，马上要defend my dissertation的Phd来说，我可能是有点发言权的。希望对楼主有些启发。首先给lz一个简单粗暴的例子吧。本世纪最牛逼的经济思想家哈耶克老先生一个数学公式都没有用。数学在经济/金融中起到一个… ");
                    break;
                default:
                    question.setUserName("儿歌日");
                    question.setTitle("西伯利亚永冻层的三万年巨型病毒是怎么被复活的？");
                    question.setContent("谢邀。我老板手里有不少巨型病毒，是从合作者那里拿到的，我就捡一些已经发表了的说说吧，都是总结自同行的工作。我就对问题中的关键词进行解读：复活，巨型病毒，史前。一般来说，我们认为病毒并不是生命的一种形式，因此很少用“复活”这个词，援引Wikipe");
                    break;
            }
            list.add(question);
        }
        adapter = new QuestionAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(activity, QuestionDetailActivity.class));
            }
        });


    }
}
