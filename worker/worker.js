/**
 * Entry Script
 */

const path = require('path');

const dotEnvConfigs = {
    path: '.env',
}
require('@babel/register');
require('@babel/polyfill');
require('dotenv').config(dotEnvConfigs);
require('./worker.entry');
