#!/usr/bin/env groovy

import groovy.transform.ToString

Result resultUsingIfs(Board board){
    if(board.anyLineFilledWith(Token.X) || board.anyColumnFilledWith(Token.X) || board.anyDiagonalFilledWith(Token.X)) return Result.XWins;
    if(board.anyLineFilledWith(Token.O) || board.anyColumnFilledWith(Token.O) || board.anyDiagonalFilledWith(Token.O)) return Result.OWins;
    if(board.notFilledYet()) return Result.NotOverYet;
    return Result.Draw;
}


Result resultUsingRules(Board board, rulesToResults){
    def firstItemWhoseRuleApplies = rulesToResults.find{ ruleToResult -> (ruleToResult.key.apply(board)) }
    return firstItemWhoseRuleApplies.value
}

class XWinsRule{
    boolean apply(board){
        return board.anyLineFilledWith(Token.X) || board.anyColumnFilledWith(Token.X) || board.anyDiagonalFilledWith(Token.X)
    }
}

class OWinsRule{
    boolean apply(board){
        return board.anyLineFilledWith(Token.O) || board.anyColumnFilledWith(Token.O) || board.anyDiagonalFilledWith(Token.O)
    }
}

class DefaultRule{
    boolean apply(board){
        return true
    }
}


class NotFilledYetRule{
    boolean apply(board){
        return board.notFilledYet()
    }
}

enum Result{
    NotOverYet,
    Draw,
    XWins,
    OWins
}


enum Token{
    Empty,
    X,
    O
}

@ToString()
class Board{
    def content = [[Token.Empty] * 3] * 3

    def notFilledYet(){
        content.any{line ->
           line.any{cell ->
                cell == Token.Empty
            } 
        }
    }


    def anyLineFilledWith(token){
        return anyFilledWith(lines(content), token)
    }

    def anyColumnFilledWith(token){
        return anyFilledWith(columns(content), token)
    }

    def anyDiagonalFilledWith(token){
        return anyFilledWith(diagonals(content), token)
    }

    private def anyFilledWith(collection, token){
        collection.any { item ->
            item.every { cell -> cell == token }
        }
    }

    private def rotate(array){
        return array.transpose()
    }

    private def lines(array){
        return array
    }

    private def columns(array){
        return lines(rotate(array))
    }

    private def diagonals(array){
        def size = array.size() - 1
        def mainDiagonal = (0..size).collect().inject([]){ diagonal, index -> 
            diagonal + array[index][index]
        }

        def secondaryDiagonal = (0..size).collect().inject([]){ diagonal, index -> 
            diagonal + array[index][size - index]
        }

        return [mainDiagonal, secondaryDiagonal]
    }
}


def rulesToResults = [(new XWinsRule()) : Result.XWins, (new OWinsRule()) : Result.OWins, (new NotFilledYetRule()) : Result.NotOverYet, (new DefaultRule()) : Result.Draw]


def emptyBoard = new Board()
assert resultUsingIfs(emptyBoard) == Result.NotOverYet
assert resultUsingRules(emptyBoard, rulesToResults) == Result.NotOverYet
println "Result for board $emptyBoard is ${resultUsingIfs(emptyBoard)}"

def unfinishedBoard = new Board(
    content: [
                [Token.X, Token.Empty, Token.O],
                [Token.O, Token.Empty, Token.X],
                [Token.Empty, Token.Empty, Token.Empty]
    ]
)

assert resultUsingIfs(unfinishedBoard) == Result.NotOverYet
assert resultUsingRules(unfinishedBoard, rulesToResults) == Result.NotOverYet
println "Result for board $unfinishedBoard is ${resultUsingIfs(unfinishedBoard)}"

def xWonBoard = new Board(
    content: [
                [Token.O, Token.O, Token.X],
                [Token.O, Token.X, Token.Empty],
                [Token.X, Token.Empty, Token.Empty]
    ]
)

assert resultUsingIfs(xWonBoard) == Result.XWins
assert resultUsingRules(xWonBoard, rulesToResults) == Result.XWins
println "Result for board $xWonBoard is ${resultUsingIfs(xWonBoard)}"

def oWonBoard = new Board(
    content: [
                [Token.O, Token.Empty, Token.X],
                [Token.O, Token.X, Token.Empty],
                [Token.O, Token.Empty, Token.Empty]
    ]
)

assert resultUsingIfs(oWonBoard) == Result.OWins
assert resultUsingRules(oWonBoard, rulesToResults) == Result.OWins
println "Result for board $oWonBoard is ${resultUsingIfs(oWonBoard)}"

def drawBoard = new Board(
    content: [
                [Token.O, Token.X, Token.O],
                [Token.X, Token.O, Token.X],
                [Token.X, Token.O, Token.X]
    ]
)
assert resultUsingIfs(drawBoard) == Result.Draw
assert resultUsingRules(drawBoard, rulesToResults) == Result.Draw
println "Result for board $drawBoard is ${resultUsingIfs(drawBoard)}"
