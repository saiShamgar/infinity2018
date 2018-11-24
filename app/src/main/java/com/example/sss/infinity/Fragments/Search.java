package com.example.sss.infinity.Fragments;



import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sss.infinity.Adapters.Horizantal_rv;
import com.example.sss.infinity.Adapters.SearchAdapter;
import com.example.sss.infinity.Adapters.ServicesAdapter;
import com.example.sss.infinity.MainActivity;
import com.example.sss.infinity.ProductListAdapter;
import com.example.sss.infinity.R;
import com.example.sss.infinity.RecyclerViewClickListener;
import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.helpers.RecyclerItemClickListener;
import com.example.sss.infinity.models.Category;
import com.example.sss.infinity.models.Items;
import com.example.sss.infinity.models.ProductViewModel;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment
{
    private SearchView searchView;
    RecyclerView recyclerView;

    private ProductViewModel viewModel;
    private ProductListAdapter adapter;

    List<ProductDetails> productDetails;


    public Search()
    {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Search");
        View rootview= inflater.inflate(R.layout.fragment_search, container, false);

        searchView = rootview.findViewById(R.id.search_material);

        recyclerView = rootview.findViewById(R.id.search_material_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Search.this.getActivity()));
        recyclerView.setHasFixedSize(true);
//        searchprogress.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this.getActivity()).get(ProductViewModel.class);

        adapter = new ProductListAdapter(this.getActivity());

        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subscribeUi(adapter,s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                subscribeUi(adapter,s);
                return true;
            }
        });

        return rootview;
    }

    private void subscribeUi(ProductListAdapter adapter, String s) {

        viewModel.getSearchListLiveData(s).observe(this.getActivity(), new Observer<PagedList<ProductDetails>>() {
            @Override
            public void onChanged(@Nullable PagedList<ProductDetails> productDetails) {
                if(!productDetails.isEmpty()){
                    adapter.submitList(productDetails);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(0);
                }else {
                    Log.e("hello: ",""+productDetails);
                    adapter.submitList(null);
                }
            }
        });

//        Toast.makeText(this.getActivity(),""+s,Toast.LENGTH_SHORT).show();

    }

}
