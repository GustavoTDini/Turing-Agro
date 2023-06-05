package com.example.turing_agro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.turing_agro.models.Calendar
import com.example.turing_agro.models.Finance
import com.example.turing_agro.models.Food
import com.example.turing_agro.models.Supply
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import java.io.Serializable

class GeneratingActivity : AppCompatActivity() {

    val fornecedores = arrayOf("Milhão", "Arroz da Sorte", "Feijão do Bão", "Café Energia Pura", "Tri-go", "Soja tudo", "Mandiocas Bravas", "Bovinos Indianos", "Granja Torta")

    val insumos = arrayOf("Semente de Milho", "Semente de Arroz", "Semente de Feijão", "Semente de Café", "Semente de Trigo", "Semente de Soja", "Semente de Mandioca", "Vaca", "Frango")
    val valor_insumos = arrayOf(5, 6, 7, 10, 3, 4, 8, 500, 15)
    val insumosImages = arrayOf(R.drawable.semente_milho, R.drawable.semente_arroz, R.drawable.semente_feijao,
        R.drawable.semente_cafe, R.drawable.semente_trigo, R.drawable.semente_soja, R.drawable.semente_mandioca, R.drawable.vaca, R.drawable.frango)
    val alimentos = arrayOf("Milho", "Arroz", "Feijão", "Café", "Trigo", "Soja", "Mandioca", "Leite", "Ovos")
    val valor_alimentos = arrayOf(15, 12, 20, 18, 16, 12, 15, 30, 12)
    val alimentosImages = arrayOf(R.drawable.milho, R.drawable.arroz, R.drawable.feijao, R.drawable.cafe, R.drawable.trigo,
        R.drawable.soja, R.drawable.mandioca, R.drawable.leite, R.drawable.ovos)

    val datas = arrayOf("30/06", "30/07", "30/08", "30/09")

    val acoes = arrayOf("Preparar a Terra", "Fertilizar a Terra", "Fazer o Plantio", "Colheita")

    val acoes_animais = arrayOf("Preparar o recinto", "Trazer os Animais", "Vacinação e cuidados", "Inicio da coleta")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generating)
        val pointsList: MutableList<LatLng>  = intent.getSerializableExtra("points") as MutableList<LatLng>
        var polygonsList: ArrayList<JSONObject> = ArrayList()
        val suppliersList: ArrayList<Supply> = ArrayList()
        val calendarList: ArrayList<Calendar> = ArrayList()
        val incomeList: ArrayList<Finance> = ArrayList()
        val outcomeList: ArrayList<Finance> = ArrayList()
        val foodList: ArrayList<Food> = ArrayList()

        val randomList = (0..8).shuffled().take(4)

        var crop = 1

        for (i in randomList){
            val quantidadeInsumos = (1..30).random()
            val quantidadeComida = (30..90).random()

            val valorConsumo = (0..50).random()
            val valorVenda = 100 - valorConsumo;

            // Create Random Supply
            val supply = Supply(fornecedores[i], insumos[i], insumosImages[i], "Área $crop", quantidadeInsumos)
            suppliersList.add(supply)

            // Create Random Calendar
            for (j in 0..3){
                if (i < 6){
                    val calendar = Calendar(datas[j], "Área $crop", acoes[j])
                    calendarList.add(calendar)
                } else{
                    val calendar = Calendar(datas[j], "Área $crop", acoes_animais[j])
                    calendarList.add(calendar)
                }
            }

            val outcome = Finance(insumos[i], quantidadeInsumos, quantidadeInsumos*valor_insumos[i])
            outcomeList.add(outcome)

            val income = Finance(alimentos[i], quantidadeComida, quantidadeComida*valor_alimentos[i])
            incomeList.add(income)

            // Create Random Food
            val food = Food(alimentos[i], alimentosImages[i], valorConsumo, valorVenda)
            foodList.add(food)

            crop ++
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val plannerIntent = Intent(this, PlannerActivity::class.java)
            plannerIntent.putExtra("suppliers",suppliersList as Serializable)
            plannerIntent.putExtra("calendar",calendarList as Serializable)
            plannerIntent.putExtra("food",foodList as Serializable)
            plannerIntent.putExtra("income",incomeList as Serializable)
            plannerIntent.putExtra("outcome",outcomeList as Serializable)
            startActivity(plannerIntent)
        }, 10000)
    }
}