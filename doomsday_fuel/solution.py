# This solves the fuel creation efficiency problem, which is based on
# Absorbing Markov Chains, the following Wikipedia page helped me get a
# deeper understanding of the underlying problem and provided quidance for
# the implementation
# https://en.wikipedia.org/wiki/Absorbing_Markov_chain
from fractions import Fraction

# Extract Q and R matrices from the canonical matrix


def QRMatrices(canonical_matrix, non_terminals, terminals):
    Q = []
    R = []
    for i in non_terminals:
        temp_row_Q = []
        temp_row_R = []
        for j in non_terminals:
            temp_row_Q.append(canonical_matrix[i][j])
        for j in terminals:
            temp_row_R.append(canonical_matrix[i][j])
        Q.append(temp_row_Q)
        R.append(temp_row_R)
    return Q, R

# Find greatest common divisor of two numbers


def greatestCommonDivisor(a, b):
    if b == 0:
        return a
    else:
        return greatestCommonDivisor(b, a % b)

# Extract absorption probability with respect to the given
# initial state


def extractAbsorptionProbability(B, initial_state):

    target_state = B[initial_state]
    fractions = [Fraction(i).limit_denominator() for i in target_state]

    lowest_common_multiple = fractions[0].denominator
    for i in fractions:
        lowest_common_multiple *= i.denominator / \
            greatestCommonDivisor(lowest_common_multiple, i.denominator)

    # Scale scale up the nominators by the lowest common multiple
    fractions = [(i*lowest_common_multiple).numerator for i in fractions]

    fractions.append(lowest_common_multiple)

    return fractions

# Matrix multiplication


def multiplyMatrices(A, B):
    M = [[0 for col in range(len(B[0]))] for row in range(len(A))]
    for rowA in range(len(A)):
        for colB in range(len(B[0])):
            for rowB in range(len(B)):
                M[rowA][colB] += A[rowA][rowB] * B[rowB][colB]
    return M

# Matrix substraction


def substractMatrices(A, B):
    S = []
    for row in range(len(A)):
        temp_row = []
        for col in range(len(A)):
            temp_row.append(A[row][col] - B[row][col])
        S.append(temp_row)
    return S

# Matrix inverse generation based on Gaussian elimination


def inverseMatrix(M):

    # Append the appropriately sized identity matrix to the received matrix
    size = len(M)
    elimination_matrix = []
    identity_matrix = identityMatrix(size)
    for row in range(size):
        temp_row = []
        for col in range(2 * size):
            if col < size:
                temp_row.append(M[row][col])
            else:
                temp_row.append(identity_matrix[row][col - size])
        elimination_matrix.append(temp_row)

    # Perform Gaussian elimination
    for i in range(size):
        for j in range(size):
            if i != j:
                ratio = elimination_matrix[j][i] / elimination_matrix[i][i]
                for k in range(size * 2):
                    elimination_matrix[j][k] -= ratio * \
                        elimination_matrix[i][k]

    # Reduce diagonal elements to 1
    for i in range(size):
        divisor = elimination_matrix[i][i]
        for j in range(size * 2):
            elimination_matrix[i][j] /= divisor

    # Extract the inverse matrix from the elimination matrix
    inverse = []

    for i in range(size):
        temp_row = []
        for j in range(size, size * 2):
            temp_row.append(elimination_matrix[i][j])
        inverse.append(temp_row)

    return inverse

# Generate indentity matrix with the given size


def identityMatrix(size):
    I = [[0 for col in range(size)] for row in range(size)]
    for i in range(size):
        I[i][i] = 1
    return I

# Calculate fundamental matrix


def fundamentalMatrix(Q):
    return inverseMatrix(substractMatrices(identityMatrix(len(Q)), Q))

# Calulcate canonical matrix, replace integer values with float for easier processing


def canonicalMatrix(M):

    for row in range(len(M)):
        row_sum = 0.0
        for col in range(len(M[row])):
            row_sum += M[row][col]
        if row_sum != 0:
            for val in range(len(M[row])):
                M[row][val] /= row_sum
    return M


def solution(m):
    # Return [1,1] if the size is 1
    if len(m) == 1:
        return [1, 1]
    canonical_matrix = canonicalMatrix(m)

    # Find non-terminal and terminal states
    non_terminals = []
    terminals = []

    for row in range(len(m)):
        isTerminal = True
        for col in range(len(m[row])):
            if m[row][col] != 0:
                isTerminal = False
                break
        if isTerminal:
            terminals.append(row)
        else:
            non_terminals.append(row)
    # Get Q and R matrices
    Q, R = QRMatrices(canonical_matrix, non_terminals, terminals)
    # Extract probability with respect to the 0th state
    return extractAbsorptionProbability(multiplyMatrices(fundamentalMatrix(Q), R), 0)


print(solution([
    [0, 1, 0, 0, 0, 1],
    [4, 0, 0, 3, 2, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
]))
