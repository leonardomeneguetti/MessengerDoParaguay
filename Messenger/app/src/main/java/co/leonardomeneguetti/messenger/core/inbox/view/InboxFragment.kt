package co.leonardomeneguetti.messenger.core.inbox.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.leonardomeneguetti.messenger.R
import co.leonardomeneguetti.messenger.databinding.ActiveNowItemBinding
import co.leonardomeneguetti.messenger.databinding.ActiveNowListBinding
import co.leonardomeneguetti.messenger.databinding.FragmentInboxBinding
import co.leonardomeneguetti.messenger.databinding.InboxRowItemBinding
import co.leonardomeneguetti.messenger.extensions.toCircle

class InboxFragment: Fragment(), MenuProvider {
    private lateinit var toolbarCallBack: ToolbarCallback

    private var _binding: FragmentInboxBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "Chats"
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawable = ContextCompat.getDrawable(activity, R.drawable.eu)
        activity.supportActionBar?.setHomeAsUpIndicator(drawable?.toCircle(resources, 32))

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToolbarCallback) {
            toolbarCallBack = context
            
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inboxAdapter = InboxAdapter()
        binding.rvInbox.adapter = inboxAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ActiveNowAdapter: RecyclerView.Adapter<ActiveNowAdapter.ActiveNowView>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveNowView {
            return ActiveNowView(ActiveNowItemBinding.inflate(layoutInflater, parent, false))
        }

        override fun getItemCount(): Int {
            return 15
        }

        override fun onBindViewHolder(holder: ActiveNowView, position: Int) {
        }

        private inner class ActiveNowView(val item: ActiveNowItemBinding): RecyclerView.ViewHolder(item.root)
    }

    private inner class InboxAdapter: RecyclerView.Adapter<InboxAdapter.InboxView>() {
        private val scrollState = mutableMapOf<Int, Parcelable?>()

        override fun getItemViewType(position: Int): Int {
            return if(position > 0) 1 else 0
        }

        override fun onViewRecycled(holder: InboxView) {
            super.onViewRecycled(holder)
            if (holder is ActiveNowView) {
                val key = holder.layoutPosition
                scrollState[key] = holder.view.rvActiveNow.layoutManager?.onSaveInstanceState()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxView {
            val view = if (viewType == 0){
                ActiveNowView(ActiveNowListBinding.inflate(layoutInflater, parent, false))
            } else {
                RowView(InboxRowItemBinding.inflate(layoutInflater, parent, false))
            }

            return view
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: InboxView, position: Int) {
            holder.bind()
        }

        abstract inner class InboxView(view: View) : RecyclerView.ViewHolder(view){
            abstract fun bind()
        }

        private inner class RowView(view: InboxRowItemBinding): InboxView(view.root) {
            override fun bind() {
            }
        }

        private inner class ActiveNowView(val view: ActiveNowListBinding): InboxView(view.root) {
            override fun bind() {
                val key = layoutPosition
                val state = scrollState[key]
                if(state != null){
                    view.rvActiveNow.layoutManager?.onRestoreInstanceState(state)
                } else {
                    val activeAdapter = ActiveNowAdapter()
                    view.rvActiveNow.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    view.rvActiveNow.adapter = activeAdapter
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val menuHost: MenuHost = requireActivity()
        menuHost.removeMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.inbox_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            android.R.id.home -> {
                toolbarCallBack.goToProfile()
                return true
            }
            R.id.new_action -> {
                toolbarCallBack.goToNewMessage()
                return true
            }
            else -> return false
        }
    }
}