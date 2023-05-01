package br.com.fiap25mob.mbamobile.presentation.guitars

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.databinding.FragmentGuitarsBinding
import br.com.fiap25mob.mbamobile.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuitarsFragment : Fragment(R.layout.fragment_guitars) {

    private var _binding: FragmentGuitarsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GuitarsViewModel by viewModel()
    private val args: GuitarsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuitarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.guitars?.let { guitarsEntity ->
            binding.registerGuitarBtn.text = getString(R.string.crud_update)
            binding.edtGuitarBrand.setText(guitarsEntity.brand)
            binding.edtGuitarModel.setText(guitarsEntity.model)

            binding.delBtn.visibility = View.VISIBLE
        }
        observerEvents()
        initListeners()
    }

    private fun observerEvents() {
        viewModel.guitarStateEventData.observe(viewLifecycleOwner) { guitarState ->
            when(guitarState){
                is GuitarsViewModel.GuitarState.Included,
                is GuitarsViewModel.GuitarState.Updated,
                is GuitarsViewModel.GuitarState.Deleted -> {
                    clearEditTexts()
                    hideKeyBoard()
                    requireView().requestFocus()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.ANIMATION_MODE_SLIDE).show()
        }
    }

    private fun clearEditTexts() {
        binding.edtGuitarBrand.text?.clear()
        binding.edtGuitarModel.text?.clear()
    }

    private fun hideKeyBoard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity){
            parentActivity.run { hideKeyboard() }
        }
    }

    private fun dialogConfirmDelete(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.modal_delete_confirm))
        builder
            .setPositiveButton(getString(R.string.ok_dialog)) { _, _ ->
                viewModel.deleteGuitar(args.guitars?.id ?: 0)
            }
            .setNegativeButton(getString(R.string.cancel_dialog)) {
                dialog: DialogInterface, _ -> dialog.dismiss()
            }
        builder.show()
    }

    private fun initListeners() {
        binding.registerGuitarBtn.setOnClickListener {
            val brand = binding.edtGuitarBrand.text.toString()
            val model = binding.edtGuitarModel.text.toString()
            viewModel.includeUpdateGuitar(brand, model, args.guitars?.id ?: 0)
        }

        binding.delBtn.setOnClickListener {
            dialogConfirmDelete(requireContext())
        }
    }
}