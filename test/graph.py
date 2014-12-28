# Python module for generating random directed graphs.
# Author:       Maciej 'mc' Suchecki

import random
import string
import itertools

# generates a list of (pseudo) random integers of desired length and sum
# (all of the generated integers are in between lowerBound and upperBound)
# throws ValueError from randrange() if operation is not possible (too restrict bounds)
def generateRandomIntegers(desiredCount, desiredSum, lowerBound, upperBound):
  integers = []

  # generate desired amount of integers
  for i in range(desiredCount):
    # adjust possible range of the next number - to do so, firstly we assume that all
    # next integers are going to have max value to calculate min value of the current one,
    # secondly that all next integers are going to have min value to calculate max value
    newLowerBound = desiredSum - sum(integers) - (desiredCount - i - 1) * upperBound
    newUpperBound = desiredSum - sum(integers) - (desiredCount - i - 1) * lowerBound
    newLowerBound = max(lowerBound, newLowerBound)
    newUpperBound = min(upperBound, newUpperBound)

    # generate random integer within desired range and append it to the list
    integers.append(random.randint(newLowerBound, newUpperBound))

  return integers

# generates random directed graph with desired number of vertices
# and density, after that it saves the edges to selected filename
def generateAndSaveGraphWithDesiredDensity(filename, verticesCount, density):
  graphFile = open(filename, "w")

  # generate vertices
  vertices = list(range(verticesCount))

  # calculate desired number of edges from desired graph density
  maxPossibleEdgeCount = verticesCount * (verticesCount - 1)
  desiredEdgeCount = int(density * maxPossibleEdgeCount)

  # generate random degree in range [0, verticesCount-1] for every vertex 
  # while ensuring that all of the degrees sum up to desiredEdgeCount
  degrees = generateRandomIntegers(verticesCount, desiredEdgeCount, 0, verticesCount - 1)

  # generate dictionary containing vertex and its degree pairs, then
  # iterate over it, drawing <degree> vertices from all possible ones
  # and save resulting vertex pairs (edges) to file with desired name
  for vertice, degree in dict(zip(vertices, degrees)).items():
    possibleSuccessors = list(vertices)
    possibleSuccessors.remove(vertice)     # avoid loops
    for successor in random.sample(possibleSuccessors, degree):
      graphFile.write(str(vertice) + " " + str(successor) + "\n")

  graphFile.close()

# generates random directed graph with desired number of vertices
# and degeneracy, after that it saves the edges to selected filename
def generateAndSaveGraphWithDesiredDegeneracy(filename, verticesCount, degeneracy):
  graphFile = open(filename, "w")

  # generate vertices
  vertices = list(range(verticesCount))
  possibleSuccessors = vertices

  # generate random degree in range [0, degeneracy] for every vertex to satisfy
  # degeneracy criterion (there should be at least 1 vertex with degree = deg.)
  degrees = [degeneracy]
  for _ in range(verticesCount - 1):
    degrees.append(random.randint(0, degeneracy))
  degrees.sort(reverse=True)

  # for every vertice, generate random number of its successors (less than
  # degeneracy), then take that number of vertices from successors set and
  # save resulting pairs (edges) to file with desired name as two-way edges
  # - from the rest, draw a random number to generate one-way edges
  for vertice, degree in dict(zip(vertices, degrees)).items():
    possibleSuccessors.remove(vertice)     # prevent loops and duplicate edges
    twoWaySuccessors = random.sample(possibleSuccessors, min(degree, len(possibleSuccessors)))
    oneWaySuccessors = [item for item in possibleSuccessors if item not in twoWaySuccessors]

    # generate two way edges
    for successor in twoWaySuccessors:
      graphFile.write(str(vertice) + " " + str(successor) + "\n")
      graphFile.write(str(successor) + " " + str(vertice) + "\n")

    # generate couple random one-way edges
    for successor in random.sample(oneWaySuccessors, random.randint(0, len(oneWaySuccessors))):
      graphFile.write(str(vertice) + " " + str(successor) + "\n")

  graphFile.close()

# generates multiple random graphs with desired density and saves them to files
def generateRandomGraphsWithDensity(graphsCount, verticesCount, density):
  filenamesList = []
  for number in range(0, graphsCount):
    filename = './graph' + str(number) + '.txt'
    generateAndSaveGraphWithDesiredDensity(filename, verticesCount, density)
    filenamesList.append(filename)
  return filenamesList

# generates multiple random graphs with desired degeneracy and saves them to files
def generateRandomGraphsWithDegeneracy(graphsCount, verticesCount, degeneracy):
  filenamesList = []
  for number in range(0, graphsCount):
    filename = './graph' + str(number) + '.txt'
    generateAndSaveGraphWithDesiredDegeneracy(filename, verticesCount, degeneracy)
    filenamesList.append(filename)
  return filenamesList
