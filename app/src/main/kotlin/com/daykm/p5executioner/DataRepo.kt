package com.daykm.p5executioner

import com.daykm.p5executioner.json.Persona
import javax.inject.Inject

class DataRepo @Inject constructor() {

  fun getPersonae() : List<Persona> {
    return ArrayList()
  }
}