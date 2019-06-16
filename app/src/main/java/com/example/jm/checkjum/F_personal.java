package com.example.jm.checkjum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by woavm on 2018-06-01.
 */

public class F_personal extends Fragment {   //개인서재 클래스
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personalinfo, container, false);
        ListView list1 = view.findViewById(R.id.list1);
        final String[] mid = {"관심도서", "한줄평", "개인별점", "개인정보수정","로그아웃"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mid);
        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {       //개인서재 리스트목록 클릭시 해당화면으로 이동
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getActivity(), Interestbook.class);
                        intent.putExtra("order","title");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), SimpleComment.class);
                        intent1.putExtra("order","title");
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(), IndividualStar.class);
                        intent2.putExtra("order","title");
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getActivity(), InfoChange.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(getActivity(),Login.class);
                        startActivity(intent4);
                }

            }
        });
        return view;
    }
}
