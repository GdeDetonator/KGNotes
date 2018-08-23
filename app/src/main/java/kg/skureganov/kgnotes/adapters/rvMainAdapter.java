package kg.skureganov.kgnotes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kg.skureganov.kgnotes.R;

public class rvMainAdapter extends RecyclerView.Adapter<rvMainAdapter.rvMainHolder> {

    private ArrayList<String> notesList;
    private rvMainClickListenet rvMainClickListenet;
    private int rvMainItemPosition;

    public rvMainAdapter(ArrayList<String> notesList, rvMainClickListenet rvMainClickListenet) {
        this.notesList = notesList;
        this.rvMainClickListenet = rvMainClickListenet;
    }

    @NonNull
    @Override
    public rvMainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rvmain, viewGroup, false);
        rvMainHolder holder = new rvMainHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final rvMainHolder rvMainHolder, int i) {
        rvMainHolder.rvMainTitle.setText(notesList.get(i));
        rvMainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvMainClickListenet.onRVMainClick(rvMainHolder.getAdapterPosition());
            }
        });
        rvMainHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setRvMainPosition(rvMainHolder.getAdapterPosition());
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }



    private void setRvMainPosition(int position){
        rvMainItemPosition = position;
    }
    public int getRvMainItemPosition(){
        return rvMainItemPosition;
    }
    public ArrayList<String> getNotesList() {
        return notesList;
    }
    public String getNoteTitle(int position){
        return notesList.get(position);
    }



    static class rvMainHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView rvMainTitle;

        private rvMainHolder(@NonNull View itemView) {
            super(itemView);
            rvMainTitle = itemView.findViewById(R.id.rvMainTitle);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, R.id.menuDelete, 0, R.string.menuDelete);
        }

    }



    public interface rvMainClickListenet{
        void onRVMainClick(int position);
        //void onRVMainLongClick(int position);
    }
}
