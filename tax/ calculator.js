import React, { useState, useEffect } from 'react';
import { Calculator, IndianRupee, FileText, TrendingUp, Building, Users } from 'lucide-react';

const IndianTaxCalculator = () => {
  const [salaryInputs, setSalaryInputs] = useState({
    basicSalary: '',
    hra: '',
    da: '',
    lta: '',
    otherAllowances: '',
    bonus: '',
    employerPf: '',
    gratuity: ''
  });

  const [investments, setInvestments] = useState({
    epf: '',
    elss: '',
    ppf: '',
    nsc: '',
    lifeInsurance: '',
    tuitionFee: '',
    principalRepayment: '',
    mediclaim: '',
    parentsMediclaim: '',
    nps: '',
    interestEducationLoan: '',
    donation: ''
  });

  const [houseRent, setHouseRent] = useState('');
  const [metroCity, setMetroCity] = useState(false);
  const [age, setAge] = useState('below60');
  const [regime, setRegime] = useState('compare');

  const [results, setResults] = useState({
    oldRegime: {},
    newRegime: {}
  });

  // Tax slabs for FY 2024-25
  const oldRegimeSlabs = [
    { min: 0, max: 250000, rate: 0 },
    { min: 250000, max: 500000, rate: 5 },
    { min: 500000, max: 1000000, rate: 20 },
    { min: 1000000, max: Infinity, rate: 30 }
  ];

  const newRegimeSlabs = [
    { min: 0, max: 300000, rate: 0 },
    { min: 300000, max: 700000, rate: 5 },
    { min: 700000, max: 1000000, rate: 10 },
    { min: 1000000, max: 1200000, rate: 15 },
    { min: 1200000, max: 1500000, rate: 20 },
    { min: 1500000, max: Infinity, rate: 30 }
  ];

  const seniorCitizenSlabs = [
    { min: 0, max: 300000, rate: 0 },
    { min: 300000, max: 500000, rate: 5 },
    { min: 500000, max: 1000000, rate: 20 },
    { min: 1000000, max: Infinity, rate: 30 }
  ];

  const calculateGrossSalary = () => {
    const basic = parseFloat(salaryInputs.basicSalary) || 0;
    const hra = parseFloat(salaryInputs.hra) || 0;
    const da = parseFloat(salaryInputs.da) || 0;
    const lta = parseFloat(salaryInputs.lta) || 0;
    const others = parseFloat(salaryInputs.otherAllowances) || 0;
    const bonus = parseFloat(salaryInputs.bonus) || 0;

    return basic + hra + da + lta + others + bonus;
  };

  const calculateHRAExemption = () => {
    const basic = parseFloat(salaryInputs.basicSalary) || 0;
    const hra = parseFloat(salaryInputs.hra) || 0;
    const rent = parseFloat(houseRent) || 0;

    if (rent === 0) return 0;

    const exemption1 = hra;
    const exemption2 = rent - (basic * 0.1);
    const exemption3 = metroCity ? basic * 0.5 : basic * 0.4;

    return Math.min(exemption1, exemption2, exemption3);
  };

  const calculateTaxableIncome = (isOldRegime) => {
    const grossSalary = calculateGrossSalary();
    const employerPf = parseFloat(salaryInputs.employerPf) || 0;
    const gratuity = Math.min(parseFloat(salaryInputs.gratuity) || 0, 2000000);

    let taxableIncome = grossSalary - employerPf - gratuity;

    if (isOldRegime) {
      // Standard deduction
      taxableIncome -= 50000;

      // HRA exemption
      const hraExemption = calculateHRAExemption();
      taxableIncome -= hraExemption;

      // LTA exemption (assuming actual travel)
      const ltaExemption = Math.min(parseFloat(salaryInputs.lta) || 0, 100000);
      taxableIncome -= ltaExemption;

      // Section 80C deductions
      const section80C = Math.min(
        (parseFloat(investments.epf) || 0) +
        (parseFloat(investments.elss) || 0) +
        (parseFloat(investments.ppf) || 0) +
        (parseFloat(investments.nsc) || 0) +
        (parseFloat(investments.lifeInsurance) || 0) +
        (parseFloat(investments.tuitionFee) || 0) +
        (parseFloat(investments.principalRepayment) || 0),
        150000
      );
      taxableIncome -= section80C;

      // Section 80D - Medical insurance
      const section80D = Math.min(
        (parseFloat(investments.mediclaim) || 0) +
        (parseFloat(investments.parentsMediclaim) || 0),
        age === 'senior' ? 50000 : 25000
      );
      taxableIncome -= section80D;

      // Section 80CCD(1B) - NPS
      const section80CCD1B = Math.min(parseFloat(investments.nps) || 0, 50000);
      taxableIncome -= section80CCD1B;

      // Section 80E - Education loan interest
      taxableIncome -= parseFloat(investments.interestEducationLoan) || 0;

      // Section 80G - Donations
      const donations = parseFloat(investments.donation) || 0;
      taxableIncome -= Math.min(donations, grossSalary * 0.1);

    } else {
      // New regime - only standard deduction
      taxableIncome -= 75000; // Increased standard deduction for new regime
    }

    return Math.max(taxableIncome, 0);
  };

  const calculateTax = (taxableIncome, isOldRegime) => {
    const slabs = isOldRegime ?
      (age === 'senior' ? seniorCitizenSlabs : oldRegimeSlabs) :
      newRegimeSlabs;

    let tax = 0;
    let remainingIncome = taxableIncome;

    for (const slab of slabs) {
      if (remainingIncome <= 0) break;

      const taxableInSlab = Math.min(remainingIncome, slab.max - slab.min);
      tax += (taxableInSlab * slab.rate) / 100;
      remainingIncome -= taxableInSlab;
    }

    return tax;
  };

  const calculateCessAndSurcharge = (tax, taxableIncome) => {
    let surcharge = 0;

    if (taxableIncome > 5000000 && taxableIncome <= 10000000) {
      surcharge = tax * 0.1;
    } else if (taxableIncome > 10000000 && taxableIncome <= 20000000) {
      surcharge = tax * 0.15;
    } else if (taxableIncome > 20000000 && taxableIncome <= 50000000) {
      surcharge = tax * 0.25;
    } else if (taxableIncome > 50000000) {
      surcharge = tax * 0.37;
    }

    const totalTaxWithSurcharge = tax + surcharge;
    const cess = totalTaxWithSurcharge * 0.04;

    return { surcharge, cess, totalTax: totalTaxWithSurcharge + cess };
  };

  const calculateResults = () => {
    const grossSalary = calculateGrossSalary();

    // Old Regime Calculation
    const oldTaxableIncome = calculateTaxableIncome(true);
    const oldBasicTax = calculateTax(oldTaxableIncome, true);
    const oldCessAndSurcharge = calculateCessAndSurcharge(oldBasicTax, oldTaxableIncome);

    // New Regime Calculation
    const newTaxableIncome = calculateTaxableIncome(false);
    const newBasicTax = calculateTax(newTaxableIncome, false);
    const newCessAndSurcharge = calculateCessAndSurcharge(newBasicTax, newTaxableIncome);

    const oldRegimeResult = {
      grossSalary,
      taxableIncome: oldTaxableIncome,
      basicTax: oldBasicTax,
      surcharge: oldCessAndSurcharge.surcharge,
      cess: oldCessAndSurcharge.cess,
      totalTax: oldCessAndSurcharge.totalTax,
      netSalary: grossSalary - oldCessAndSurcharge.totalTax,
      hraExemption: calculateHRAExemption()
    };

    const newRegimeResult = {
      grossSalary,
      taxableIncome: newTaxableIncome,
      basicTax: newBasicTax,
      surcharge: newCessAndSurcharge.surcharge,
      cess: newCessAndSurcharge.cess,
      totalTax: newCessAndSurcharge.totalTax,
      netSalary: grossSalary - newCessAndSurcharge.totalTax,
      hraExemption: 0
    };

    setResults({
      oldRegime: oldRegimeResult,
      newRegime: newRegimeResult
    });
  };

  useEffect(() => {
    calculateResults();
  }, [salaryInputs, investments, houseRent, metroCity, age]);

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0
    }).format(amount);
  };

  const handleSalaryChange = (field, value) => {
    setSalaryInputs(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleInvestmentChange = (field, value) => {
    setInvestments(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const ResultCard = ({ title, result, isRecommended }) => (
    <div className={`bg-white rounded-lg shadow-lg p-6 ${isRecommended ? 'ring-2 ring-green-500' : ''}`}>
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-xl font-bold text-gray-800">{title}</h3>
        {isRecommended && (
          <span className="bg-green-100 text-green-800 px-2 py-1 rounded-full text-sm font-semibold">
            Recommended
          </span>
        )}
      </div>

      <div className="space-y-3">
        <div className="flex justify-between">
          <span className="text-gray-600">Gross Salary:</span>
          <span className="font-semibold">{formatCurrency(result.grossSalary)}</span>
        </div>

        <div className="flex justify-between">
          <span className="text-gray-600">Taxable Income:</span>
          <span className="font-semibold">{formatCurrency(result.taxableIncome)}</span>
        </div>

        <div className="flex justify-between">
          <span className="text-gray-600">Basic Tax:</span>
          <span className="font-semibold">{formatCurrency(result.basicTax)}</span>
        </div>

        {result.surcharge > 0 && (
          <div className="flex justify-between">
            <span className="text-gray-600">Surcharge:</span>
            <span className="font-semibold">{formatCurrency(result.surcharge)}</span>
          </div>
        )}

        <div className="flex justify-between">
          <span className="text-gray-600">Health & Education Cess:</span>
          <span className="font-semibold">{formatCurrency(result.cess)}</span>
        </div>

        <div className="border-t pt-3">
          <div className="flex justify-between text-lg font-bold">
            <span>Total Tax:</span>
            <span className="text-red-600">{formatCurrency(result.totalTax)}</span>
          </div>

          <div className="flex justify-between text-lg font-bold text-green-600">
            <span>Net Salary:</span>
            <span>{formatCurrency(result.netSalary)}</span>
          </div>
        </div>
      </div>
    </div>
  );

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-4">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <Calculator className="mr-3 h-8 w-8 text-blue-600" />
            <h1 className="text-3xl font-bold text-gray-800">Indian Tax Calculator</h1>
          </div>
          <p className="text-gray-600">Calculate your tax liability under Old vs New Tax Regime (FY 2024-25)</p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
          {/* Salary Details */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <div className="flex items-center mb-4">
              <Building className="mr-2 h-5 w-5 text-blue-600" />
              <h2 className="text-xl font-semibold text-gray-800">Salary Details</h2>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Basic Salary</label>
                <input
                  type="number"
                  value={salaryInputs.basicSalary}
                  onChange={(e) => handleSalaryChange('basicSalary', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Annual Basic Salary"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">HRA</label>
                <input
                  type="number"
                  value={salaryInputs.hra}
                  onChange={(e) => handleSalaryChange('hra', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="House Rent Allowance"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Dearness Allowance</label>
                <input
                  type="number"
                  value={salaryInputs.da}
                  onChange={(e) => handleSalaryChange('da', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="DA"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">LTA</label>
                <input
                  type="number"
                  value={salaryInputs.lta}
                  onChange={(e) => handleSalaryChange('lta', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Leave Travel Allowance"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Other Allowances</label>
                <input
                  type="number"
                  value={salaryInputs.otherAllowances}
                  onChange={(e) => handleSalaryChange('otherAllowances', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Other Allowances"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Bonus</label>
                <input
                  type="number"
                  value={salaryInputs.bonus}
                  onChange={(e) => handleSalaryChange('bonus', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Annual Bonus"
                />
              </div>
            </div>

            <div className="mt-4 grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">House Rent (Monthly)</label>
                <input
                  type="number"
                  value={houseRent}
                  onChange={(e) => setHouseRent(e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Monthly rent paid"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Age Category</label>
                <select
                  value={age}
                  onChange={(e) => setAge(e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="below60">Below 60 years</option>
                  <option value="senior">Senior Citizen (60+)</option>
                </select>
              </div>
            </div>

            <div className="mt-4">
              <label className="flex items-center">
                <input
                  type="checkbox"
                  checked={metroCity}
                  onChange={(e) => setMetroCity(e.target.checked)}
                  className="mr-2"
                />
                <span className="text-sm text-gray-700">Living in Metro City (Mumbai, Delhi, Chennai, Kolkata)</span>
              </label>
            </div>
          </div>

          {/* Investments & Deductions */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <div className="flex items-center mb-4">
              <TrendingUp className="mr-2 h-5 w-5 text-green-600" />
              <h2 className="text-xl font-semibold text-gray-800">Investments & Deductions</h2>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">EPF (Employee)</label>
                <input
                  type="number"
                  value={investments.epf}
                  onChange={(e) => handleInvestmentChange('epf', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="EPF contribution"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">ELSS Mutual Funds</label>
                <input
                  type="number"
                  value={investments.elss}
                  onChange={(e) => handleInvestmentChange('elss', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="ELSS investment"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">PPF</label>
                <input
                  type="number"
                  value={investments.ppf}
                  onChange={(e) => handleInvestmentChange('ppf', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="PPF contribution"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Life Insurance</label>
                <input
                  type="number"
                  value={investments.lifeInsurance}
                  onChange={(e) => handleInvestmentChange('lifeInsurance', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Life insurance premium"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Home Loan Principal</label>
                <input
                  type="number"
                  value={investments.principalRepayment}
                  onChange={(e) => handleInvestmentChange('principalRepayment', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Principal repayment"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">NPS (80CCD1B)</label>
                <input
                  type="number"
                  value={investments.nps}
                  onChange={(e) => handleInvestmentChange('nps', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="NPS additional contribution"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Medical Insurance</label>
                <input
                  type="number"
                  value={investments.mediclaim}
                  onChange={(e) => handleInvestmentChange('mediclaim', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Health insurance premium"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Parents Medical Insurance</label>
                <input
                  type="number"
                  value={investments.parentsMediclaim}
                  onChange={(e) => handleInvestmentChange('parentsMediclaim', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Parents health insurance"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Education Loan Interest</label>
                <input
                  type="number"
                  value={investments.interestEducationLoan}
                  onChange={(e) => handleInvestmentChange('interestEducationLoan', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Education loan interest"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Charitable Donations</label>
                <input
                  type="number"
                  value={investments.donation}
                  onChange={(e) => handleInvestmentChange('donation', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Donations (80G)"
                />
              </div>
            </div>
          </div>
        </div>

        {/* Results */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <ResultCard
            title="Old Tax Regime"
            result={results.oldRegime}
            isRecommended={results.oldRegime.totalTax < results.newRegime.totalTax}
          />

          <ResultCard
            title="New Tax Regime"
            result={results.newRegime}
            isRecommended={results.newRegime.totalTax < results.oldRegime.totalTax}
          />
        </div>

        {/* Savings Summary */}
        <div className="mt-8 bg-white rounded-lg shadow-lg p-6">
          <div className="flex items-center mb-4">
            <IndianRupee className="mr-2 h-5 w-5 text-green-600" />
            <h2 className="text-xl font-semibold text-gray-800">Tax Savings Summary</h2>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-center">
            <div className="bg-blue-50 p-4 rounded-lg">
              <h3 className="text-lg font-semibold text-blue-800">Old Regime Tax</h3>
              <p className="text-2xl font-bold text-blue-600">{formatCurrency(results.oldRegime.totalTax || 0)}</p>
            </div>

            <div className="bg-green-50 p-4 rounded-lg">
              <h3 className="text-lg font-semibold text-green-800">New Regime Tax</h3>
              <p className="text-2xl font-bold text-green-600">{formatCurrency(results.newRegime.totalTax || 0)}</p>
            </div>

            <div className="bg-yellow-50 p-4 rounded-lg">
              <h3 className="text-lg font-semibold text-yellow-800">You Save</h3>
              <p className="text-2xl font-bold text-yellow-600">
                {formatCurrency(Math.abs((results.oldRegime.totalTax || 0) - (results.newRegime.totalTax || 0)))}
              </p>
              <p className="text-sm text-gray-600">
                {(results.oldRegime.totalTax || 0) < (results.newRegime.totalTax || 0) ? 'in Old Regime' : 'in New Regime'}
              </p>
            </div>
          </div>
        </div>

        {/* Disclaimer */}
        <div className="mt-8 bg-yellow-50 border border-yellow-200 rounded-lg p-4">
          <div className="flex items-start">
            <FileText className="mr-2 h-5 w-5 text-yellow-600 mt-0.5" />
            <div>
              <h3 className="text-sm font-semibold text-yellow-800 mb-1">Important Disclaimer</h3>
              <p className="text-sm text-yellow-700">
                This calculator provides estimates based on current tax laws for FY 2024-25.
                Actual tax calculations may vary based on specific circumstances.
                Please consult a qualified tax professional for accurate tax planning and filing.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default IndianTaxCalculator;