/*
 * Copyright © 2014 TU Berlin (emma@dima.tu-berlin.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example
package graphs

import model.Edge

import org.emmalanguage.SparkAware
import org.emmalanguage.api._
import org.emmalanguage.io.csv._

class SparkTransitiveClosureIntegrationSpec extends BaseTransitiveClosureIntegrationSpec with SparkAware {

  override def transitiveClosure(input: String, csv: CSV): Set[Edge[Long]] =
    emma.onSpark {
      // read in set of edges
      val edges = DataBag.readCSV[Edge[Long]](input, csv)
      // build the transitive closure
      val paths = TransitiveClosure(edges)
      // return the closure as local set
      paths.fetch().toSet
    }
}