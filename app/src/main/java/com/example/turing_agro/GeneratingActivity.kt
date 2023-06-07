package com.example.turing_agro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.turing_agro.models.Calendar
import com.example.turing_agro.models.Finance
import com.example.turing_agro.models.Food
import com.example.turing_agro.models.Polygon
import com.example.turing_agro.models.Supply
import com.example.turing_agro.models.Vertice
import com.google.android.gms.maps.model.LatLng
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
        val polygonsList: ArrayList<Polygon> = ArrayList()
        val polygonsCenters: ArrayList<Vertice> = ArrayList()
        var centroidLat = 0.0
        var centroidLng = 0.0
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

            val outcome = Finance(insumos[i], quantidadeInsumos,
                (quantidadeInsumos*valor_insumos[i]).toFloat(), 0F
            )
            outcomeList.add(outcome)

            val income = Finance(alimentos[i], quantidadeComida,
                (quantidadeComida*valor_alimentos[i]).toFloat(), 0F
            )
            incomeList.add(income)

            // Create Random Food
            val food = Food(alimentos[i], alimentosImages[i], valorConsumo, valorVenda)
            foodList.add(food)

            crop ++
        }

        val centroid = findCentroid(pointsList as ArrayList<LatLng>)
        centroidLat = centroid.latitude
        centroidLng = centroid.longitude

        val pointsSize = pointsList.size
        val vertices = (pointsSize/4) + 1
        val resto = pointsSize%4
        val verticesList  = mutableListOf(0, 0, 0, 0)

        for (i in 1..4){
            var putVertice = vertices
            if (i<= resto){
                putVertice += 1
            }
            verticesList[i-1] = putVertice
        }
        Log.d("teste", "onCreate: $verticesList")

        var startVertice = 0
        for (i in 0 until verticesList.size){
            val points = ArrayList<Vertice>()
            for (j in 0  until  verticesList[i]){
                var index = startVertice + j
                if (index == pointsSize){
                    index = 0
                }
                val latitude = pointsList[index].latitude
                val longitude = pointsList[index].longitude
                points.add(Vertice(latitude,longitude))
            }
            points.add(centroid)
            val polygon = Polygon(points)
            polygonsList.add(polygon)
            startVertice += (verticesList[i]-1)
        }

        for (poligon in polygonsList){
            polygonsCenters.add(findCentroid(poligon.toLatLngList()))
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val plannerIntent = Intent(this, PlannerActivity::class.java)
            plannerIntent.putExtra("suppliers",suppliersList as Serializable)
            plannerIntent.putExtra("calendar",calendarList as Serializable)
            plannerIntent.putExtra("food",foodList as Serializable)
            plannerIntent.putExtra("income",incomeList as Serializable)
            plannerIntent.putExtra("outcome",outcomeList as Serializable)
            plannerIntent.putExtra("poligons",polygonsList as Serializable)
            plannerIntent.putExtra("centers",polygonsCenters as Serializable)
            plannerIntent.putExtra("latitude",centroidLat)
            plannerIntent.putExtra("longitude",centroidLng)
            startActivity(plannerIntent)
        }, 3000)
    }

    fun findCentroid(poligon: List<LatLng>): Vertice{
        var latitudeSum = 0.0
        var longitudeSum = 0.0
        val vertices = poligon.size
        for (vertice in poligon){
            latitudeSum += vertice.latitude
            longitudeSum += vertice.longitude
        }
        return Vertice(latitudeSum/vertices, longitudeSum/vertices)
    }

}