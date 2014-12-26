# Python module for generating random directed graphs.
# Author:       Maciej 'mc' Suchecki

import random
import string
import itertools

# function from Itertools Recipes
def pairwise(iterable):
  "s -> (s0,s1), (s1,s2), (s2, s3), ..."
  a, b = itertools.tee(iterable)
  next(b, None)
  return zip(a, b)

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

# generates random directed graph with desired number of verticles
# and density, after that it saves the edges to selected filename
def saveRandomGraphToFile(filename, verticlesCount, density):
  graphFile = open(filename, "w")

  # generate verticles
  verticles = list(range(verticlesCount))

  # calculate desired number of edges from desired graph density
  maxPossibleEdgeCount = verticlesCount * (verticlesCount - 1)
  desiredEdgeCount = int(density * maxPossibleEdgeCount)

  # generate random degree in range [0, verticlesCount-1] for every vertex 
  # ensuring that all of the degrees sum to desiredEdgeCount
  degrees = generateRandomIntegers(verticlesCount, desiredEdgeCount, 0, verticlesCount - 1)

  # for every verticle, generate random number of its successors,
  # then take that number of verticles from verticles set as successors set
  # and save resulting pairs (edges) to file with desired name
  for verticle, degree in dict(zip(verticles, degrees)).items():
    possibleSuccessors = list(verticles)
    possibleSuccessors.remove(verticle)     # avoid loops
    for successor in random.sample(possibleSuccessors, degree):
      graphFile.write(str(verticle) + " " + str(successor) + "\n")

  graphFile.close()

# generates multiple random graphs and saves them to files
def saveRandomGraphsToFiles(graphsCount, verticlesCount, density):
  filenamesList = []
  for number in range(0, graphsCount):
    filename = './graph' + str(number) + '.txt'
    saveRandomGraphToFile(filename, verticlesCount, density)
    filenamesList.append(filename)
  return filenamesList
