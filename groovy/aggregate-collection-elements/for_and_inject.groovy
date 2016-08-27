#!/usr/bin/env groovy

import java.util.Currency
import groovy.transform.EqualsAndHashCode

def computeTotalSalaryWithLoop(employees){
    def amount = Money.euro(0)
    for(employee in employees) amount += employee.salary
    return amount
}

def computeTotalSalaryWithInject(employees){
    return employees.inject(Money.euro(0)) {amount, employee -> amount + employee.salary}
}

@EqualsAndHashCode
class Money{
    BigDecimal amount
    Currency currency

    static def euro(amount){
        return new Money(amount: amount, currency: Currency.getInstance("EUR"))
    }
    
    def plus(money){
        assert money.currency == this.currency
        return new Money(amount: amount + money.amount, currency: currency)
    }

    String toString(){
        return "$amount $currency"
    }
}


class Employee{
    Money salary
}


def salariesInEuro = [700.0, 800.0, 1300.0, 2400.0, 2400.0, 3400.0, 3600.0]
def employees = salariesInEuro.collect{Money.euro(it)}.collect{new Employee(salary: it)}

println "Compute total salary with loops"
def totalFromLoop = computeTotalSalaryWithLoop(employees)
assert totalFromLoop == Money.euro(salariesInEuro.sum())
println "Total=$totalFromLoop"


println "Compute total salary with map"
def total = computeTotalSalaryWithInject(employees)
assert total == Money.euro(salariesInEuro.sum())
println "Total=$total"
