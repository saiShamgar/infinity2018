package com.example.sss.infinity.Fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.sss.infinity.R;
import com.example.sss.infinity.RecyclerViewClickListener;
import com.example.sss.infinity.helpers.RecyclerItemClickListener;
import com.example.sss.infinity.models.Category;
import com.example.sss.infinity.models.Items;
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
    SimpleRecyclerView simpleRecyclerView;
    ProgressBar searchprogress;
    EditText editTextSearch;
    RecyclerView horizantalRv;
    int selected_position;

    int textcount=0;

    ArrayList<String> names;
    private BottomNavigationView mPercentView;
    private boolean status=false;


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

        simpleRecyclerView=rootview.findViewById(R.id.recyclerView);
        searchprogress=(ProgressBar) rootview.findViewById(R.id.searchprogress);

        horizantalRv=(RecyclerView)rootview.findViewById(R.id.horizantalsearchRV);
        names = new ArrayList<String>();
        names.add("1");
        names.add("2");
        names.add("3");
        names.add("4");
        names.add("5");
        names.add("6");
        names.add("7");
        names.add("8");
        names.add("9");
        names.add("10");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        horizantalRv.setLayoutManager(layoutManager);
       /* RecyclerViewClickListener listener = (view, position) ->
        {

            Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();

           // view.findViewById(R.id.imageselected).setVisibility(View.VISIBLE);

        };
        */

        Horizantal_rv horizantal_rv=new Horizantal_rv(getActivity(),names,status);

        horizantalRv.setAdapter(horizantal_rv);




       // Horizantal_rv adapter = new Horizantal_rv(listener);

     /*   simpleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 && mPercentView.isShown()) {
                    mPercentView.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    mPercentView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        */



        horizantalRv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), horizantalRv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selected_position=horizantalRv.getChildAdapterPosition(view);
                if ((selected_position == position)&& !(view.findViewById(R.id.imageselected).isShown()))
                {
                    view.findViewById(R.id.imageselected).setVisibility(View.VISIBLE);




                }
                else
                {
                    view.findViewById(R.id.imageselected).setVisibility(View.GONE);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"long clicked",Toast.LENGTH_SHORT).show();
            }
        }));



        searchprogress.setVisibility(View.GONE);
        this.addRecyclerHeaders();
        this.bindData();

        editTextSearch=(EditText)rootview.findViewById(R.id.edtsearch);

        editTextSearch.addTextChangedListener(new TextWatcher()
        {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filter(s.toString());
            }
        });


        return rootview;
    }

    private void filter(String s)
    {
        simpleRecyclerView.removeAllCells();

        List<Items> list = getData();

        List<SearchAdapter>   cells = new ArrayList<>();
        for (Items items : list)
        {
            SearchAdapter cell = new SearchAdapter(items);

            if (items.getName().equalsIgnoreCase(s))
            {

                cells.add(cell);
            }

        }
        if (cells.size()==0)
        {
            simpleRecyclerView.removeAllCells();
            Toast.makeText(getActivity(),"Searching...",Toast.LENGTH_SHORT).show();
            bindData();
        }


        simpleRecyclerView.addCells(cells);

    }


    private void addRecyclerHeaders()
    {
        SectionHeaderProvider<Items> sh=new SimpleSectionHeaderProvider<Items>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull Items list, int i) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.header, null, false);
                TextView textView =  view.findViewById(R.id.headerTxt);
                textView.setText(list.getCategoryName());

               // selected_position=horizantalRv.getChildAdapterPosition(view);
              if(i==2)
              {
                  status=true;
              }



                return view;
            }

            @Override
            public boolean isSameSection(@NonNull Items list, @NonNull Items next)
            {

                return list.getCategoryId() == next.getCategoryId();
            }
            // Optional, whether the header is sticky, default false
            @Override
            public boolean isSticky()
            {
                return true;
            }
        };
        simpleRecyclerView.setSectionHeader(sh);


    }


    private void bindData()
    {
        List<Items> lists = getData();
        //CUSTOM SORT ACCORDING TO CATEGORIES
        Collections.sort(lists, new Comparator<Items>(){
            public int compare(Items list, Items next) {
                return list.getCategoryId() - next.getCategoryId();
            }
        });
        List<SearchAdapter>   cells = new ArrayList<>();

        //LOOP THROUGH LISTS INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
        for (Items LIST : lists) {
            SearchAdapter cell = new SearchAdapter(LIST);
            // There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>
            cell.setOnCellClickListener2(new SimpleCell.OnCellClickListener2<SearchAdapter, SearchAdapter.ViewHolder, Items>() {
                @Override
                public void onCellClicked(SearchAdapter CELL, SearchAdapter.ViewHolder viewHolder, Items item) {
                    Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            cell.setOnCellLongClickListener2(new SimpleCell.OnCellLongClickListener2<SearchAdapter, SearchAdapter.ViewHolder, Items>()
            {
                @Override
                public void onCellLongClicked(SearchAdapter CELL, SearchAdapter.ViewHolder viewHolder, Items item) {
                    Toast.makeText(getActivity(), item.getDescription(), Toast.LENGTH_SHORT).show();

                }
            });
            cells.add(cell);
        }
        simpleRecyclerView.addCells(cells);


    }



    private ArrayList<Items> getData()
    {
        ArrayList<Items> LIST=new ArrayList<>();

        //CREATE CATEGORIES
        Category Beauty=new Category(0,"Beauty Services");
        Category Cakes=new Category(1,"Cup Cakes");
        Category Dogs=new Category(2,"Dogs");
        Category others=new Category(3,"Other Services");
        Category Packages=new Category(4,"Packages");

        //INSTANTIATE GALAXY OBJECTS AND ADD THEM TO GALAXY LIST
        Items g=new Items("Facial",
                "International skin lighting facial",
               R.drawable.ic_home_black_24dp,"3000/-",Beauty);
        LIST.add(g);

        g=new Items("Facial",
                "International serum gold facial",
                R.drawable.ic_home_black_24dp,"3000/-",Beauty);
        LIST.add(g);

        g=new Items("Facial",
                "Sara Skin Polishing",
                R.drawable.ic_home_black_24dp,"3500/-",Beauty);
        LIST.add(g);

        g=new Items("Facial",
                "O3+ diamond facial",
                R.drawable.ic_home_black_24dp,"3300/-",Beauty);
        LIST.add(g);

        g=new Items("Facial",
                "International Birdal facial",
                R.drawable.ic_home_black_24dp,"4000/-",Beauty);
        LIST.add(g);

        g=new Items("Facial",
                "Skin lightning and brightning facial",
                R.drawable.ic_home_black_24dp,"3500/-",Beauty);
        LIST.add(g);

        g=new Items("Facial",
                "Party glow Facial",
                R.drawable.ic_home_black_24dp,"1800/-",Beauty);
        LIST.add(g);

        g=new Items("Facial",
                "Birdal Skin lighting facial",
                R.drawable.ic_home_black_24dp,"1900/-",Beauty);
        LIST.add(g);

        g=new Items("piercing",
                "piercing Rs 300 for one member and 500 for two",
                R.drawable.ic_home_black_24dp,"300/- or 500/-",others);
        LIST.add(g);

        g=new Items("Temporary tattoo",
                "Temporary tattoo 50/-",
                R.drawable.ic_home_black_24dp,"50/-",others);
        LIST.add(g);

        g=new Items("Permanent Tattoo",
                "800 onwards per inch and 1000 onwards per 2inch",
                R.drawable.andromeda,"800/- or 1000/-",others);
        LIST.add(g);

        g=new Items("Hair Styles Male",
                "Hair cut",
                R.drawable.ic_home_black_24dp,"250/-",Beauty);
        LIST.add(g);

        g=new Items("Hair Styles Male",
                "Change over hair cut",
                R.drawable.ic_home_black_24dp,"350/-",Beauty);
        LIST.add(g);

        g=new Items("Hair Styles Male",
                "beard trim",
                R.drawable.ic_home_black_24dp,"100/-",Beauty);
        LIST.add(g);

        g=new Items("Hair Styles Male",
                "Beard Styling",
                R.drawable.ic_home_black_24dp,"150/-",Beauty);
        LIST.add(g);

        g=new Items("Hair Styles Male",
                "Clean shave",
                R.drawable.ic_home_black_24dp,"100/-",Beauty);
        LIST.add(g);

        g=new Items("Hair Styles Male",
                "Baby Hair cut",
                R.drawable.ic_home_black_24dp,"150",Beauty);
        LIST.add(g);

        g=new Items("Colorings Male",
                "Basic Hair Color",
                R.drawable.ic_home_black_24dp,"700/-",Beauty);
        LIST.add(g);


        g=new Items("Colorings Male",
                "inoa hair color",
                R.drawable.ic_home_black_24dp,"900/-",Beauty);
        LIST.add(g);

        g=new Items("Colorings Male",
                "Global hair color/fashion color",
                R.drawable.ic_home_black_24dp,"1800/-",Beauty);
        LIST.add(g);

        g=new Items("Colorings Male",
                "Root Touch up",
                R.drawable.ic_home_black_24dp,"400/-",Beauty);
        LIST.add(g);
        g=new Items("Colorings Male",
                "Highlights one streak",
                R.drawable.ic_home_black_24dp,"150/-",Beauty);
        LIST.add(g);

        g=new Items("Colorings Male",
                "Henna",
                R.drawable.ic_home_black_24dp,"300/-",Beauty);
        LIST.add(g);
        return LIST;
    }


}
