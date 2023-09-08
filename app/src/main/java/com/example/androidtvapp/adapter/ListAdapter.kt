import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtvapp.R
import com.example.androidtvapp.model.DashBoardDataResponseItem

class ListAdapter(private val data: List<DashBoardDataResponseItem>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class FirstItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_actual)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val actual: TextView = itemView.findViewById(R.id.tv_actual)
        val assembly: TextView = itemView.findViewById(R.id.tv_assembly)
        val line: TextView = itemView.findViewById(R.id.tv_line)
        val target: TextView = itemView.findViewById(R.id.tv_target)
        val skuCode: TextView = itemView.findViewById(R.id.tv_sku_code)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) HEADER_ITEM else NORMAL_ITEM
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            HEADER_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return FirstItemViewHolder(view)
            }

            NORMAL_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_normal_item, parent, false)
                return ViewHolder(view)
            }

            else-> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_normal_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val model = data?.get(position)
            holder.assembly.text = model?.displayName
            holder.actual.text = model?.skuCount
            holder.skuCode.text = model?.skuCode
            holder.target.text = model?.target
            holder.line.text = model?.isActive
        }
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    companion object {
        const val HEADER_ITEM = 1
        const val NORMAL_ITEM = 2
    }

}
