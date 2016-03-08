## Randomized search heuristics for the travelling thief problem

Keywords: Evolutionary Computation, optimization problems, traveling salesperson problem, knapsack problem.

### Introduction

Over the past 50 years, many optimization problems have been studied by researchers from different fields of study: applied mathematics, operations research, meta-heuristics, and artificial intelligence. Most of the studied problems belong to the class of problems known as NP-hard, so that solving “large” instances of these problems to optimality is not possible [1]. These problems include the travelling salesman problem and knapsack problems. Both problems have been considered in numerous theoretical and experimental studies, and very effective solvers are known that perform well on a variety of benchmarks.

However, real-world problems usually consist of two or more sub-problems that interdepend significantly. This interdependence is responsible for the complexity of the real-world problems, while this type of complexity in current benchmark problems is missing. In order to close the gap between theory and practice, a new problem, called the travelling thief problem (TTP), was introduced in [1] as a combination of two well-known problems: the knapsack problem and the travelling salesman problem.

In this project our goal is to study (implement and test) a class of meta-heuristics methods designed for solving the travelling thief problem: these methods (many of which are inspired by nature) are iterative. They start from constructing one or more complete solutions and gradually improve these solutions during an iterative process. One main advantage is that one can track the quality of the solutions computed at each iteration, so the user can stop the algorithm as soon as acceptable results are identified.

### Definition of the Travelling Thief problem (TTP)

The Travelling Thief Problem was introduced in [1] and is defined as follows (see [2] for a more detailed presentation). You are given a set of N cities (numbered from 1 to n), each containing a collection of items (having each a given value/price and weight). The traveling thief must perform a tour of the cities collecting the items: its goal is to find a tour (not necessarily the shortest one) that provides the maximal profit. He has to take care of the total weight of its knapsack: its capacity is limited, and moreover it affects the speed of the thief.

### Problem parameters

The input consists of the following parameters:

- the set of distances d(i,j) (i,j <=n) between any pair of cities,
- a set of items Mi=(1, ..., m(i)) for every city i,
- each item k (located in city i) is characterized by: its value p(i,k), and its weight w(i,k),
- the maximum possible weight W of the knapsack,
- the maximal and minimum speeds of the thief (denoted Vmax and Vmin resp.),
- the renting rate R: the amount of money the thief must pay for renting the knapsack in an unit of time.

### Goals of the programming project

The main goal of the project is to implement and test existing simple heuristics (from [2]), as well as to design and test your own new heuristics for the solution of the traveling thief problem. More precisely, students are invited to:

- implement and test the constructive heuristic described in [2] (see Sec. 4.1) which consists to first compute a good TSP tour and then to construct a solution in order to maximize the objective function value according to a given tour;
- implement and test the iterative heuristic based on random local search and evolutionary algorithms (see Sec. 4.2, in [2]);
- to design and implement a new heuristic for TTP and to perform experimental tests and comparison with existing heuristics.
 
### References

[1] The travelling thief problem: the first step in the transition from theoretical problems to realistic problems, Mohammad Reza Bonyadi, Zbigniew Michalewicz, Luigi Barone. IEEE Congress on Evolutionary Computation 2013: 1037-1044

[2] A comprehensive benchmark set and heuristics for the traveling thief problem. Sergey Polyakovskiy, Mohammad Reza Bonyadi, Markus Wagner, Zbigniew Michalewicz, Frank Neumann. GECCO 2014: 477-484

[3] Approximate Approaches to the Traveling Thief Problem. Hayden Faulkner, Sergey Polyakovskiy, Tom Schultz, Markus Wagner. GECCO 2015: 385-392

[4] CEC Competition at IEEE WCCI 2014: Optimization of problems with multiple interdependent components [(www)](http://cs.adelaide.edu.au/~optlog/CEC2014Comp/)
