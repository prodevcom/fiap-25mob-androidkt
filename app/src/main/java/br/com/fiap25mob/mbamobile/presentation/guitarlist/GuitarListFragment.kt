package br.com.fiap25mob.mbamobile.presentation.guitarlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.databinding.FragmentGuitarListBinding
import br.com.fiap25mob.mbamobile.presentation.guitarlist.adapter.GuitarListAdapter
import br.com.fiap25mob.mbamobile.utils.navigateWithAnimations
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuitarListFragment : Fragment(R.layout.fragment_guitar_list) {

    private var _binding: FragmentGuitarListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GuitarListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuitarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGuitars()
    }

    private fun observeViewModelEvents() {
        viewModel.allGuitarsEvent.observe(viewLifecycleOwner){ guitarList ->
            binding.emptyListGuitarMessage.isVisible = guitarList.isEmpty()
            binding.rvGuitars.isVisible = guitarList.isNotEmpty()
            val guitarListAdapter = GuitarListAdapter(guitarList).apply {
                onItemClick = { guitars ->
                    val directions = GuitarListFragmentDirections
                        .actionGuitarListFragmentToGuitarsFragment(guitars)
                    findNavController().navigateWithAnimations(directions)
                }
            }

            with(binding.rvGuitars) {
                setHasFixedSize(true)
                adapter = guitarListAdapter
            }
        }
    }

    private fun setListeners(){
        binding.fbAdd.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_guitarListFragment_to_guitarsFragment)
        }
    }
}