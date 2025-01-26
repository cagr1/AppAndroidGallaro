package com.gallardoromero.task1

import kotlin.math.*
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import  android.widget.EditText
import android.text.InputType
import com.google.android.material.textfield.TextInputEditText
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {

    // Declara etMultilineInput como una propiedad de la clase
    private lateinit var etMultilineInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Inicializa etMultilineInput correctamente
        etMultilineInput = findViewById<TextInputEditText>(R.id.etMultilineInput)

        // Vincula los botones
        val btnExpression1 = findViewById<MaterialButton>(R.id.btnExpression1)
        val btnExpression2 = findViewById<MaterialButton>(R.id.btnExpression2)
        val btnExpression3 = findViewById<MaterialButton>(R.id.btnExpression3)
        val btnExpression4 = findViewById<MaterialButton>(R.id.btnExpression4)
        val btnAnalyzeValues = findViewById<MaterialButton>(R.id.btnAnalyzeValues)
        val btnMultiplicationTable = findViewById<MaterialButton>(R.id.btnMultiplicationTable)
        val btnFactorial = findViewById<MaterialButton>(R.id.btnFactorial)

        // Asigna acciones a los botones

        btnExpression1.setOnClickListener {
            etMultilineInput.setText("")
            showInputDialog(
                title = "Expresión 1: √(a√b) - √(b√a)",
                inputs = listOf("Ingrese a", "Ingrese b")
            ) { values ->
                val result = expression1(values[0], values[1])
                etMultilineInput.setText("Resultado Expresión 1: $result\n${etMultilineInput.text}")
            }
        }

        btnExpression2.setOnClickListener {
            etMultilineInput.setText("")
            showInputDialog(
                title = "Expresión 2: (3ab)/∜(a²b³c)",
                inputs = listOf("Ingrese a", "Ingrese b", "Ingrese c")
            ) { values ->
                val result = expression2(values[0], values[1], values[2])
                etMultilineInput.setText("Resultado Expresión 2: $result\n${etMultilineInput.text}")
            }
        }

        btnExpression3.setOnClickListener {
            etMultilineInput.setText("")
            showInputDialog(
                title = "Expresión 3: √(a+b)/(√(a+b)-√(a−b))",
                inputs = listOf("Ingrese a", "Ingrese b")
            ) { values ->
                val result = expression3(values[0], values[1])
                etMultilineInput.setText("Resultado Expresión 3: $result\n${etMultilineInput.text}")
            }
        }

        btnExpression4.setOnClickListener {
            etMultilineInput.setText("")
            showInputDialog(
                title = "Expresión 4: √((X₂−X₁)² + (Y₂−Y₁)²)",
                inputs = listOf("X₁", "Y₁", "X₂", "Y₂")
            ) { values ->
                val result = expression4(values[0], values[1], values[2], values[3])
                etMultilineInput.setText("Resultado Expresión 4: $result\n${etMultilineInput.text}")
            }
        }


        btnAnalyzeValues.setOnClickListener {
            etMultilineInput.setText("")
            analyzeValues()
        }

        btnMultiplicationTable.setOnClickListener {
            etMultilineInput.setText("")
            val result = multiplicationTable(5)
            etMultilineInput.setText(result)
        }

        btnFactorial.setOnClickListener {
            etMultilineInput.setText("")
            val factorialResult = factorial(5)
            etMultilineInput.setText("Factorial de 5 es: $factorialResult")
        }
    }

    private fun showInputDialog(
        title: String,
        inputs: List<String>,
        callback: (List<Double>) -> Unit
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 16, 32, 16)
        }

        val editTexts = inputs.map { inputHint ->
            EditText(this).apply {
                hint = inputHint
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                layout.addView(this)
            }
        }

        builder.setView(layout)
        builder.setPositiveButton("Calcular") { _, _ ->
            val values = editTexts.map { it.text.toString().toDoubleOrNull() ?: 0.0 }
            callback(values)
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
    // Expresión 1
    private fun expression1(a: Double, b: Double): Double {
        val val1 = sqrt(a * sqrt(b))
        val val2 = sqrt(b * sqrt(a))
        return val1 - val2
    }

    // Expresión 2
    private fun expression2(a: Double, b: Double, c: Double): Double {
        val val1 = 3 * a * b
        val val2 = (a * a * b * b * b * c).pow(1.0 / 4.0)
        return val1 / val2
    }

    // Expresión 3
    private fun expression3(a: Double, b: Double): Double {
        val cuaAB = sqrt(a + b)
        val cuaAMenosB = sqrt(a - b)
        return cuaAB / (cuaAB - cuaAMenosB)
    }

    // Expresión 4
    private fun expression4(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        return sqrt((x2 - x1).pow(2.0) + (y2 - y1).pow(2.0))
    }

    // Función para analizar valores
    private fun analyzeValues() {
        val values = mutableListOf<Int>() // Lista para almacenar los valores ingresados
        var currentIndex = 0 // Índice del valor actual que se está solicitando

        // Función para mostrar el cuadro de diálogo y solicitar un valor
        fun showInputDialog() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Ingrese el valor ${currentIndex + 1} de 10")

            // Crear un EditText para que el usuario ingrese el valor
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            builder.setView(input)

            // Configurar el botón "Aceptar"
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val value = input.text.toString().toIntOrNull() ?: 0 // Convertir el valor a entero
                values.add(value) // Agregar el valor a la lista
                currentIndex++ // Incrementar el índice

                if (currentIndex < 10) {
                    // Si aún faltan valores, mostrar el cuadro de diálogo nuevamente
                    showInputDialog()
                } else {
                    // Si ya se ingresaron los 10 valores, calcular y mostrar los resultados
                    val negatives = values.count { it < 0 }
                    val positives = values.count { it > 0 }
                    val multiplesOf15 = values.count { it % 15 == 0 }
                    val sumEvens = values.filter { it % 2 == 0 }.sum()

                    val result = """
                        Resultados:
                        a) Valores negativos: $negatives
                        b) Valores positivos: $positives
                        c) Múltiplos de 15: $multiplesOf15
                        d) Suma de valores pares: $sumEvens
                    """.trimIndent()

                    // Mostrar los resultados en el TextInputEditText
                    etMultilineInput.setText(result)
                }
                dialog.dismiss() // Cerrar el cuadro de diálogo
            }

            // Configurar el botón "Cancelar"
            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss() // Cerrar el cuadro de diálogo
            }

            // Mostrar el cuadro de diálogo
            builder.show()
        }

        // Iniciar el proceso mostrando el primer cuadro de diálogo
        showInputDialog()
    }

    // Función para la tabla de multiplicar
    private fun multiplicationTable(number: Int): String {
        val table = StringBuilder()
        for (i in 1..10) {
            table.append("$number x $i = ${number * i}\n")
        }
        return table.toString()
    }

    // Función para el factorial
    private fun factorial(number: Int): Long {
        return if (number == 0 || number == 1) 1 else number * factorial(number - 1)
    }
}




