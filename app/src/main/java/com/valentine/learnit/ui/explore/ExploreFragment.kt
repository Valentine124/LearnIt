package com.valentine.learnit.ui.explore

import android.app.AlertDialog
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.valentine.learnit.R
import com.valentine.learnit.dashboard.CourseDashboardActivity
import com.valentine.learnit.data.NetworkState
import com.valentine.learnit.databinding.FragmentExploreBinding
import com.valentine.learnit.db.RecentCourses
import com.valentine.learnit.internet.Result
import com.valentine.learnit.main.BASE_URL
import com.valentine.learnit.main.COURSE_URL_STRING
import com.valentine.learnit.main.LearnItApplication
import com.valentine.learnit.ui.business.BusinessActivity
import com.valentine.learnit.ui.categories.CategoriesActivity
import com.valentine.learnit.ui.designs.DesignActivity
import com.valentine.learnit.ui.development.DevelopmentActivity
import com.valentine.learnit.ui.financeaccounting.FinanceAndAccountingActivity
import com.valentine.learnit.ui.healthfitness.HealthAndFitnessActivity
import com.valentine.learnit.ui.itsoftware.ItAndSoftwareActivity
import com.valentine.learnit.ui.marketing.MarketingActivity
import com.valentine.learnit.ui.profile.ProfileActivity
import com.valentine.learnit.ui.setting.SettingsActivity
import com.valentine.learnit.ui.teachingacademics.TeachingAndAcademicsActivity

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val model: ExploreViewModel by viewModels {
        ExploreFactory((context?.applicationContext as LearnItApplication).repository)
    }
    private val database = Firebase.database.reference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        binding.exploreToolbar.apply {
            title = "Explore"
            addMenuProvider(object : MenuProvider{
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_items, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.user_profile -> {
                            startActivity(Intent(requireContext(), ProfileActivity::class.java))
                            true
                        }
                        R.id.settingsFragment -> {
                            startActivity(Intent(requireContext(), SettingsActivity::class.java))
                            true
                        }
                        else -> false
                    }
                }
            })
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_items, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.user_profile -> {
                        startActivity(Intent(requireContext(), ProfileActivity::class.java))
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        })

        binding.btnDesign.setClickListener(DesignActivity::class.java)
        binding.btnDevelopment.setClickListener(DevelopmentActivity::class.java)
        binding.btnBusiness.setClickListener(BusinessActivity::class.java)
        binding.btnFinanceAcc.setClickListener(FinanceAndAccountingActivity::class.java)
        binding.btnHealthFit.setClickListener(HealthAndFitnessActivity::class.java)
        binding.btnMarketing.setClickListener(MarketingActivity::class.java)
        binding.btnItSoft.setClickListener(ItAndSoftwareActivity::class.java)
        binding.btnTeachingAcade.setClickListener(TeachingAndAcademicsActivity::class.java)

        firebaseAuth = FirebaseAuth.getInstance()


        model.developmentCourses.observe(viewLifecycleOwner) { results ->

            //binding data from the views in explore fragment layout

            binding.devImage1.load(results[0].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.devTitle1.text = results[0].title
            results[0].visible_instructors.forEach {
                binding.devTutor1.text = it.name
            }
            binding.devPrice1.text = results[0].price
            binding.devCard1.setClickListener(results[0])

            binding.devImage2.load(results[1].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.devTitle2.text = results[1].title
            results[1].visible_instructors.forEach {
                binding.devTutor2.text = it.name
            }
            binding.devPrice2.text = results[1].price
            binding.devCard2.setClickListener(results[1])

            binding.devImage3.load(results[2].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.devTitle3.text = results[2].title
            results[2].visible_instructors.forEach {
                binding.devTutor3.text = it.name
            }
            binding.devPrice3.text = results[2].price
            binding.devCard3.setClickListener(results[2])

            binding.devImage4.load(results[3].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.devTitle4.text = results[3].title
            results[3].visible_instructors.forEach {
                binding.devTutor4.text = it.name
            }
            binding.devPrice4.text = results[3].price
            binding.devCard4.setClickListener(results[3])

            binding.devImage5.load(results[4].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.devTitle5.text = results[4].title
            results[4].visible_instructors.forEach {
                binding.devTutor5.text = it.name
            }
            binding.devPrice5.text = results[4].price
            binding.devCard5.setClickListener(results[4])
        }

        model.designCourses.observe(viewLifecycleOwner) { results ->

            //binding data from the views in explore fragment layout

            binding.desImage1.load(results[0].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.desTitle1.text = results[0].title
            results[0].visible_instructors.forEach {
                binding.desTutor1.text = it.name
            }
            binding.desPrice1.text = results[0].price
            binding.desCard1.setClickListener(results[0])

            binding.desImage2.load(results[1].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.desTitle2.text = results[1].title
            results[1].visible_instructors.forEach {
                binding.desTutor2.text = it.name
            }
            binding.desPrice2.text = results[1].price
            binding.desCard2.setClickListener(results[1])

            binding.desImage3.load(results[2].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.desTitle3.text = results[2].title
            results[2].visible_instructors.forEach {
                binding.desTutor3.text = it.name
            }
            binding.desPrice3.text = results[2].price
            binding.desCard3.setClickListener(results[2])

            binding.desImage4.load(results[3].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.desTitle4.text = results[3].title
            results[3].visible_instructors.forEach {
                binding.desTutor4.text = it.name
            }
            binding.desPrice4.text = results[3].price
            binding.desCard4.setClickListener(results[3])

            binding.desImage5.load(results[4].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.desTitle5.text = results[4].title
            results[4].visible_instructors.forEach {
                binding.desTutor5.text = it.name
            }
            binding.desPrice5.text = results[4].price
            binding.desCard5.setClickListener(results[4])
        }

        model.itAndSoftwareCourses.observe(viewLifecycleOwner) { results ->

            //binding data from the views in explore fragment layout

            binding.itsImage1.load(results[0].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.itsTitle1.text = results[0].title
            results[0].visible_instructors.forEach {
                binding.itsTutor1.text = it.name
            }
            binding.itsPrice1.text = results[0].price
            binding.itsCard1.setClickListener(results[0])

            binding.itsImage2.load(results[1].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.itsTitle2.text = results[1].title
            results[1].visible_instructors.forEach {
                binding.itsTutor2.text = it.name
            }
            binding.itsPrice2.text = results[1].price
            binding.itsCard2.setClickListener(results[1])

            binding.itsImage3.load(results[2].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.itsTitle3.text = results[2].title
            results[2].visible_instructors.forEach {
                binding.itsTutor3.text = it.name
            }
            binding.itsPrice3.text = results[2].price
            binding.itsCard3.setClickListener(results[2])

            binding.itsImage4.load(results[3].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.itsTitle4.text = results[3].title
            results[3].visible_instructors.forEach {
                binding.itsTutor4.text = it.name
            }
            binding.itsPrice4.text = results[3].price
            binding.itsCard4.setClickListener(results[3])

            binding.itsImage5.load(results[4].image_480x270) { placeholder(R.drawable.ic_image_placeholder) }
            binding.itsTitle5.text = results[4].title
            results[4].visible_instructors.forEach {
                binding.itsTutor5.text = it.name
            }
            binding.itsPrice5.text = results[4].price
            binding.itsCard5.setClickListener(results[4])
        }

        model.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                NetworkState.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    if (isConnected()) {
                        showDialog()
                    }
                }
            }
        }

        binding.textSeeAllCat.setClickListener(CategoriesActivity::class.java)
        binding.seeAllDes.setClickListener(DesignActivity::class.java)
        binding.seeAllDev.setClickListener(DevelopmentActivity::class.java)
        binding.seeAllIt.setClickListener(ItAndSoftwareActivity::class.java)

        binding.btnRetry.setOnClickListener {
            displayLayout()
        }

        displayLayout()

        return binding.root
    }

    private fun showDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setIcon(R.drawable.ic_baseline_warning_24)
                setTitle("Unable to connect to the server.")
                setPositiveButton("Retry") { _, _ ->
                    displayLayout()
                }
            }
            builder.create()
        }

        alertDialog?.show()
    }


    private fun TextView.setClickListener(cls: Class<*>) {
        this.setOnClickListener {
            startActivity(Intent(requireContext(), cls))
        }
    }

    private fun Button.setClickListener(cls: Class<*>) {
        this.setOnClickListener {
            startActivity(Intent(requireContext(), cls))
        }
    }

    private fun CardView.setClickListener(course: Result) {
        this.setOnClickListener {
            val intent = Intent(requireContext(), CourseDashboardActivity::class.java)
            val currentUser = firebaseAuth.currentUser
            intent.putExtra(COURSE_URL_STRING, BASE_URL + course.url)
            startActivity(intent)

            val id = course.id
            val trackingId = course.tracking_id
            val image = course.image_480x270
            val price = course.price
            val url = course.url
            val instructors = course.visible_instructors
            val title = course.title

            val recentCourse = RecentCourses(trackingId, id, title, image, price, url, instructors)

            currentUser?.let {
                database.child("users").child(it.uid).child("visited").child("$id").setValue(recentCourse)
            }
        }
    }

    private fun isConnected(): Boolean {
        val cm = getSystemService(requireContext(), ConnectivityManager::class.java) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
    }

    private fun displayLayout() {
        if (isConnected()) {
            model.getAllCourses()
            binding.exploreLayout.visibility = View.VISIBLE
            binding.noInternetLayout.visibility = View.INVISIBLE
        } else {
            binding.exploreLayout.visibility = View.INVISIBLE
            binding.noInternetLayout.visibility = View.VISIBLE
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}