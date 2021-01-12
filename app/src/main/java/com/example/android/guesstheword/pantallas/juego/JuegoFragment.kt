/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.pantallas.juego

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.JuegoFragmentBinding

class JuegoFragment : Fragment() {
    lateinit var vistaModelo: JuegoViewModel
    private lateinit var binding: JuegoFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.juego_fragment,
                container,
                false
        )
        Log.i("juegofragment", "viewmodelprovider llamado")
        vistaModelo = ViewModelProvider(this).get(JuegoViewModel::class.java)
        binding.loConseguisteButton.setOnClickListener {
            vistaModelo.clickLoConseguiste()
        }
        binding.omitirButton.setOnClickListener {
            vistaModelo.clickOmitir()
        }
        vistaModelo.palabra.observe(viewLifecycleOwner, Observer { nuevaPalabra ->
            binding.palabraText.text = nuevaPalabra
        })
        vistaModelo.puntuacion.observe(viewLifecycleOwner, Observer { nuevaPuntacion ->
            binding.puntuacionText.text = nuevaPuntacion.toString()
        })
        vistaModelo.elEventoTermino.observe(viewLifecycleOwner, Observer { terminoElJuego ->
            if (terminoElJuego){
                juegoTerminado()
                vistaModelo.terminarJuego()
            }
        })
        return binding.root
    }
    private fun juegoTerminado() {
        val puntuacionActual = vistaModelo.puntuacion.value ?: 0
        val action = JuegoFragmentDirections.actionGameToScore(puntuacionActual)
        findNavController(this).navigate(action)
    }

}
