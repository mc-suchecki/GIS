# Script testing how changing tabu list travelling salesman problem solver
# parameters affects its performance by generating graphs and launching solver.
# Author:       Maciej 'mc' Suchecki

import os
import sys
import numpy
import graph
import timeit
import subprocess
import matplotlib.pyplot as plot

############################## PARAMETERS #################################

repetitions = 20    # how many times solver will be re-run to calculate avg

############################## FUNCTIONS ##################################

# runs the app with desired input graph
def runSolver(graphFilename):
  command = ["java", "-jar", "./GIS.jar", "-i", str(graphFilename)]
  result = subprocess.check_output(command)
  return result

# runs solver and records run time
def runSolverAndMeasureTime(graphFilename):
  start = timeit.default_timer()
  runSolver(graphFilename)
  stop = timeit.default_timer()
  time = stop - start
  return time

# runs solver multiple times with different graphs and collects average time
def collectAverageTime(graphFilenames):
  averageTime = 0
  for graph in graphFilenames:
    time = runSolverAndMeasureTime(graph)
    averageTime += time
  averageTime /= repetitions
  return averageTime

# plots a simple graph
def plotGraph(data, xlabel, ylabel, number, linestyle, marker):
  plot.figure(number)
  plot.plot(list(data.keys()), list(data.values()), linestyle=linestyle, marker=marker)
  plot.ylabel(ylabel)
  plot.xlabel(xlabel)
  plot.show()

# displays current progress on console
def displayProgress(currentNumber, lastNumber):
  sys.stdout.write("\r%d/%d" % (currentNumber, lastNumber))

################################ SCRIPT ################################

# graph properties to test
defaultSize = 60
defaultDensity = 0.5
sizes = range(5, 50)
densities = numpy.arange(0.01, 1.01, 0.01)
degeneracies = range(1, 41)

# test how changing graph size affects performance
#print("Testing performance depending on graph size...")
#results = {}
#for size in sizes:
  #displayProgress(size, sizes[-1])
  #graphFilenames = graph.generateRandomGraphsWithDensity(repetitions, size, defaultDensity)
  #results[size] = collectAverageTime(graphFilenames)
#plotGraph(results, "Rozmiar grafu (liczba wierzchołków)", "Czas wykonywania (ms)", 0, "-", "")

# test how changing graph density affects performance
#print("Testing performance depending on graph density...")
#results = {}
#for density in densities:
  #displayProgress(density * 100, len(densities))
  #graphFilenames = graph.generateRandomGraphsWithDensity(repetitions, defaultSize, density)
  #results[density] = collectAverageTime(graphFilenames)
#plotGraph(results, "Gęstość grafu (liczba krawędzi / największa możliwa liczba krawędzi)", "Czas wykonywania (ms)", 1, "", "o")

# test how changing graph density affects performance
print("Testing performance depending on graph degeneracy...")
results = {}
for degeneracy in degeneracies:
  displayProgress(degeneracy, len(degeneracies))
  graphFilenames = graph.generateRandomGraphsWithDegeneracy(repetitions, defaultSize, degeneracy)
  results[degeneracy] = collectAverageTime(graphFilenames)
plotGraph(results, "Degeneracja grafu", "Czas wykonywania (ms)", 2, "-", "")

# remove graph files
for filename in graphFilenames:
  os.remove(filename)
