import { Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import PrivateRoute from './components/PrivateRoute';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import PerfilPage from './pages/PerfilPage';
import AutomoveisPage from './pages/AutomoveisPage';
import PedidosPage from './pages/PedidosPage';
import ContratosPage from './pages/ContratosPage';
import DesignSystemPage from './pages/DesignSystemPage';

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/design" element={<DesignSystemPage />} />
      <Route
        path="/"
        element={
          <PrivateRoute>
            <Layout />
          </PrivateRoute>
        }
      >
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="perfil" element={<PerfilPage />} />
        <Route path="automoveis" element={<AutomoveisPage />} />
        <Route path="pedidos" element={<PedidosPage />} />
        <Route path="contratos" element={<ContratosPage />} />
      </Route>
      <Route path="*" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
}
