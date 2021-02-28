package fiordor.fiocca.quotations.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fiordor.fiocca.quotations.R;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private List<Quotation> quotations;
    private OnItemClickListener onItemClick;
    private OnItemLongClickListener onItemLongClick;

    public FavouriteAdapter(List<Quotation> quotations, OnItemClickListener onItemClick, OnItemLongClickListener onItemLongClick) {
        this.quotations = quotations;
        this.onItemClick = onItemClick;
        this.onItemLongClick = onItemLongClick;
    }

    public Quotation getQuoteUsingListPosition(int position) {
        return quotations.get(position);
    }

    public Quotation removeQuoteUsingListPosition(int position) {
        Quotation q = quotations.remove(position);
        notifyItemRemoved(position);
        return q;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_list_row, parent, false);
        FavouriteAdapter.ViewHolder holder = new ViewHolder(view, onItemClick, onItemLongClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        Quotation q = quotations.get(position);
        holder.quoteText.setText(q.getQuoteText());
        holder.quoteAuthor.setText(q.getQuoteAuthor());
    }

    @Override
    public int getItemCount() {
        return quotations.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView quoteText;
        public TextView quoteAuthor;
        private OnItemClickListener onItemClick;
        private OnItemLongClickListener onItemLongClick;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClick, OnItemLongClickListener onItemLongClick) {
            super(itemView);
            quoteText = itemView.findViewById(R.id.tvQuoteText);
            quoteAuthor = itemView.findViewById(R.id.tvQuoteAuthor);
            this.onItemClick = onItemClick;
            this.onItemLongClick = onItemLongClick;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClickListener(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClick.onItemLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
