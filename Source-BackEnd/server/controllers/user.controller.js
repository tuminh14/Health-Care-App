import * as userService from '../services/user.service';
import User from '../models/users.model'
import mongoose from 'mongoose'
export async function registry(req, res) {
    try {
        let options = req.body;
        let payload = await userService.registry(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}
export async function login(req, res) {
    try {
        let options = req.body;
        let payload = await userService.login(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}

export async function sendVerifyPhoneNum(req, res) {
    try {
        let options = await req.query;
        let payload = await userService.sendVerifyPhoneNum(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}

export async function verifyPhoneNum(req, res) {
    try {
        let options = await req.query;
        let payload = await userService.verifyPhoneNum(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}
export async function sendVerifyEmail(req, res) {
    try {
        let options = await req.query;
        let payload = await userService.sendVerifyEmail(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}

export async function saveStepByDay(req, res){
    try {
        let options =req.body;
        options._id = req.JWTDecoded._id;
        let payload = await userService.saveStepByDay(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}

export async function getStepByDay(req, res){
    try {
        let options ={};
        options._id = req.JWTDecoded._id;
        console.log(req.JWTDecoded._id);
        let payload = await userService.getStepByDay(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}

export async function deleteAllStep(req, res){
    try {
        let options ={};
        options._id = req.JWTDecoded._id;
        console.log(req.JWTDecoded._id);
        let payload = await userService.deleteAllStep(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}