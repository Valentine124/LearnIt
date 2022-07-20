package com.valentine.learnit.ui.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.valentine.learnit.databinding.ActivityCategoriesBinding
import com.valentine.learnit.ui.business.BusinessActivity
import com.valentine.learnit.ui.designs.DesignActivity
import com.valentine.learnit.ui.development.DevelopmentActivity
import com.valentine.learnit.ui.financeaccounting.FinanceAndAccountingActivity
import com.valentine.learnit.ui.healthfitness.HealthAndFitnessActivity
import com.valentine.learnit.ui.itsoftware.ItAndSoftwareActivity
import com.valentine.learnit.ui.lifestyle.LifestyleActivity
import com.valentine.learnit.ui.marketing.MarketingActivity
import com.valentine.learnit.ui.music.MusicActivity
import com.valentine.learnit.ui.officeproductivity.OfficeProductivityActivity
import com.valentine.learnit.ui.personaldevelopment.PersonalDevelopmentActivity
import com.valentine.learnit.ui.photographyvideo.PhotographyAndVideoActivity
import com.valentine.learnit.ui.teachingacademics.TeachingAndAcademicsActivity

class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.materialToolbar.apply {
            title = "Categories"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                this@CategoriesActivity.onBackPressed()
            }
        }

        binding.businessCard.setClickListener(BusinessActivity::class.java)
        binding.designCard.setClickListener(DesignActivity::class.java)
        binding.developmentCard.setClickListener(DevelopmentActivity::class.java)
        binding.financeAccCard.setClickListener(FinanceAndAccountingActivity::class.java)
        binding.healthFitCard.setClickListener(HealthAndFitnessActivity::class.java)
        binding.itSoftCard.setClickListener(ItAndSoftwareActivity::class.java)
        binding.lifestyleCard.setClickListener(LifestyleActivity::class.java)
        binding.marketingCard.setClickListener(MarketingActivity::class.java)
        binding.musicCard.setClickListener(MusicActivity::class.java)
        binding.officeProCard.setClickListener(OfficeProductivityActivity::class.java)
        binding.personalDevCard.setClickListener(PersonalDevelopmentActivity::class.java)
        binding.photoVidCard.setClickListener(PhotographyAndVideoActivity::class.java)
        binding.teachingAcadeCard.setClickListener(TeachingAndAcademicsActivity::class.java)
    }

    private fun CardView.setClickListener(cls: Class<*>) {
        this.setOnClickListener {
            startActivity(Intent(this@CategoriesActivity, cls))
        }
    }

}