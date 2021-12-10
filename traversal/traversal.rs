extern crate cxx;
extern crate rocksdb;
extern crate ortools;

use rocksdb::{DB};
use cxx::let_cxx_string;
use std::pin::Pin;

pub fn poc_ortools() {
    println!("=== ORTOOLS ===");
    println!("Going to solve this problem:");
    println!();
    println!("Maximize x + 10y subject to the following constraints:
        x + 7 y	≤	17.5
        x	≤	3.5
        x	≥	0
        y	≥	0
        x, y integers");
    println!();

    let mut solver = ortools::ffi::new_mpsolver();

    let_cxx_string!(x = "x");
    let_cxx_string!(y = "y");

    let var_x = solver.pin_mut().MakeIntVar(0.0, f64::INFINITY, &x);
    let var_y = solver.pin_mut().MakeIntVar(0.0, f64::INFINITY, &y);

    println!("Number of variables = {}", solver.NumVariables());

    let_cxx_string!(c0 = "c0");
    let_cxx_string!(c1 = "c1");
    let con0 = solver.pin_mut().MakeRowConstraint(-f64::INFINITY, 17.5, &c0);

    unsafe {
        Pin::new_unchecked(con0.as_mut().unwrap()).SetCoefficient(var_x, 1.0);
        Pin::new_unchecked(con0.as_mut().unwrap()).SetCoefficient(var_y, 7.0);
    }

    let con1 = solver.pin_mut().MakeRowConstraint(-f64::INFINITY, 3.5, &c1);

    unsafe {
        Pin::new_unchecked(con1.as_mut().unwrap()).SetCoefficient(var_x, 1.0);
        Pin::new_unchecked(con1.as_mut().unwrap()).SetCoefficient(var_y, 0.0);
    }

    let objective = solver.pin_mut().MutableObjective();
    unsafe {
        Pin::new_unchecked(objective.as_mut().unwrap()).SetCoefficient(var_x, 1.0);
        Pin::new_unchecked(objective.as_mut().unwrap()).SetCoefficient(var_y, 10.0);
        Pin::new_unchecked(objective.as_mut().unwrap()).SetMaximization();

    }

    let result = solver.pin_mut().Solve();
    unsafe {
        println!("Solution: result: {:?}", result);
        println!("Objective value: {}", objective.as_ref().unwrap().Value());
        println!("X = {}", var_x.as_ref().unwrap().solution_value());
        println!("Y = {}", var_y.as_ref().unwrap().solution_value());
        println!()
    }
}

pub fn poc_rocksdb() {
    println!("=== ROCKSDB ===");
    let path = "_path_for_rocksdb_storage";
    let key = b"we will";
    let value = b"miss max";

    println!("Going to open RocksDB at path '{}'; put value '{}' by key '{}' and attempt to retrieve it back",
             path, String::from_utf8(value.to_vec()).unwrap(), String::from_utf8(key.to_vec()).unwrap());
    let db = DB::open_default(path).unwrap();
    db.put(key, value).unwrap();
    match db.get(key) {
        Ok(Some(value)) => println!("retrieved value '{}'", String::from_utf8(value).unwrap()),
        Ok(None) => println!("value not found"),
        Err(e) => println!("operational problem encountered: {}", e),
    }
    db.delete(key).unwrap();
}
