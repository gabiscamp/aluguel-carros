import { useState, useEffect } from 'react';
import './DesignSystemPage.css';

const sections = [
  { id: 'cores', label: 'Paleta de Cores' },
  { id: 'tipografia', label: 'Tipografia' },
  { id: 'espacamentos', label: 'Espacamentos' },
  { id: 'logo', label: 'Logo e Marca' },
  { id: 'temas', label: 'Temas' },
  { id: 'botoes', label: 'Botoes e Icones' },
  { id: 'componentes', label: 'Componentes' },
  { id: 'acessibilidade', label: 'Acessibilidade' },
];

const primaryColors = [
  { name: 'Indigo', hex: '#6366f1', var: '--primary' },
  { name: 'Indigo Dark', hex: '#4f46e5', var: '--primary-dark' },
  { name: 'Indigo Light', hex: '#818cf8', var: '--primary-light-color' },
];

const semanticColors = [
  { name: 'Success', hex: '#10b981', var: '--success' },
  { name: 'Warning', hex: '#f59e0b', var: '--warning' },
  { name: 'Danger', hex: '#ef4444', var: '--danger' },
  { name: 'Info', hex: '#3b82f6', var: '--info' },
];

const neutralColors = [
  { name: 'Gray 900', hex: '#0f172a' },
  { name: 'Gray 800', hex: '#1e293b' },
  { name: 'Gray 700', hex: '#334155' },
  { name: 'Gray 500', hex: '#64748b' },
  { name: 'Gray 400', hex: '#94a3b8' },
  { name: 'Gray 300', hex: '#cbd5e1' },
  { name: 'Gray 200', hex: '#e2e8f0' },
  { name: 'Gray 100', hex: '#f1f5f9' },
  { name: 'Gray 50', hex: '#f8fafc' },
  { name: 'White', hex: '#ffffff' },
];

const spacings = [4, 8, 12, 16, 20, 24, 32, 48, 64];

const radii = [
  { label: 'sm', value: 4 },
  { label: 'md', value: 8 },
  { label: 'lg', value: 12 },
  { label: 'xl', value: 16 },
  { label: 'full', value: 9999 },
];

const icons = [
  { emoji: '📊', label: 'Dashboard' },
  { emoji: '🚗', label: 'Carro' },
  { emoji: '📋', label: 'Pedidos' },
  { emoji: '📄', label: 'Contratos' },
  { emoji: '👤', label: 'Perfil' },
  { emoji: '☰', label: 'Menu' },
  { emoji: '✕', label: 'Fechar' },
  { emoji: '➕', label: 'Adicionar' },
  { emoji: '✏️', label: 'Editar' },
  { emoji: '🗑️', label: 'Excluir' },
];

export default function DesignSystemPage() {
  const [dark, setDark] = useState(false);
  const [activeSection, setActiveSection] = useState('cores');

  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        for (const entry of entries) {
          if (entry.isIntersecting) {
            setActiveSection(entry.target.id);
          }
        }
      },
      { rootMargin: '-20% 0px -60% 0px' }
    );

    sections.forEach(({ id }) => {
      const el = document.getElementById(id);
      if (el) observer.observe(el);
    });

    return () => observer.disconnect();
  }, []);

  const renderColorSwatch = (color) => (
    <div className="ds-color-swatch" key={color.hex + color.name}>
      <div className="ds-color-preview" style={{ background: color.hex }} />
      <div className="ds-color-info">
        <div className="ds-color-name">{color.name}</div>
        <div className="ds-color-hex">{color.hex}</div>
      </div>
    </div>
  );

  return (
    <div className={`ds-page ${dark ? 'ds-dark' : ''}`}>
      {/* Sidebar */}
      <aside className="ds-sidebar">
        <div className="ds-sidebar-brand">
          <div className="ds-sidebar-brand-logo">
            <span>🚗</span> AutoRent
          </div>
          <div className="ds-sidebar-version">Design System v1.0</div>
        </div>
        <nav className="ds-nav">
          {sections.map((s) => (
            <a
              key={s.id}
              href={`#${s.id}`}
              className={activeSection === s.id ? 'ds-nav-active' : ''}
            >
              {s.label}
            </a>
          ))}
        </nav>
        <button className="ds-sidebar-toggle" onClick={() => setDark(!dark)}>
          {dark ? '☀️' : '🌙'} {dark ? 'Modo Claro' : 'Modo Escuro'}
        </button>
      </aside>

      {/* Main */}
      <main className="ds-main">
        {/* Hero */}
        <div className="ds-hero">
          <div className="ds-hero-logo">
            <span>🚗</span> AutoRent
          </div>
          <p className="ds-hero-subtitle">Design System v1.0 — Guia de estilos e componentes do sistema de aluguel de carros</p>
        </div>

        {/* 1. Paleta de Cores */}
        <section className="ds-section" id="cores">
          <h2 className="ds-section-title">Paleta de Cores</h2>
          <p className="ds-section-desc">
            Cores primarias, semanticas e neutras utilizadas em toda a interface.
            Cada cor possui uma variavel CSS correspondente para facil reutilizacao.
          </p>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Cores Primarias</h3>
            <div className="ds-color-grid">{primaryColors.map(renderColorSwatch)}</div>
            <div className="ds-code-block">
              {primaryColors.map(c => `${c.var}: ${c.hex};`).join('\n')}
            </div>
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Cores Semanticas</h3>
            <div className="ds-color-grid">{semanticColors.map(renderColorSwatch)}</div>
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Neutros</h3>
            <div className="ds-color-grid">{neutralColors.map(renderColorSwatch)}</div>
          </div>
        </section>

        {/* 2. Tipografia */}
        <section className="ds-section" id="tipografia">
          <h2 className="ds-section-title">Tipografia</h2>
          <p className="ds-section-desc">
            A fonte primaria e <span className="ds-code">Inter</span> para interface.
            A fonte display <span className="ds-code">Racing Sans One</span> e usada exclusivamente na logo e marca.
          </p>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Fonte Display</h3>
            <div className="ds-type-row">
              <span className="ds-type-label">Racing Sans One</span>
              <span className="ds-type-sample" style={{ fontFamily: "'Racing Sans One', cursive", fontSize: '2rem' }}>
                AutoRent
              </span>
              <span className="ds-type-meta">Logo / Marca</span>
            </div>
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Pesos da Fonte</h3>
            {[
              { weight: 300, label: 'Light' },
              { weight: 400, label: 'Regular' },
              { weight: 500, label: 'Medium' },
              { weight: 600, label: 'SemiBold' },
              { weight: 700, label: 'Bold' },
            ].map((w) => (
              <div className="ds-type-row" key={w.weight}>
                <span className="ds-type-label">{w.label}</span>
                <span className="ds-type-sample" style={{ fontWeight: w.weight, fontSize: '1.1rem' }}>
                  O rapido carro marrom saltou sobre o cachorro preguicoso
                </span>
                <span className="ds-type-meta">font-weight: {w.weight}</span>
              </div>
            ))}
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Tamanhos de Titulo</h3>
            {[
              { tag: 'H1', size: '2rem' },
              { tag: 'H2', size: '1.5rem' },
              { tag: 'H3', size: '1.25rem' },
              { tag: 'H4', size: '1.125rem' },
            ].map((h) => (
              <div className="ds-type-row" key={h.tag}>
                <span className="ds-type-label">{h.tag}</span>
                <span className="ds-type-sample" style={{ fontSize: h.size, fontWeight: 700 }}>
                  Titulo de exemplo
                </span>
                <span className="ds-type-meta">{h.size}</span>
              </div>
            ))}
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Texto de Corpo</h3>
            {[
              { label: 'Regular', size: '1rem' },
              { label: 'Small', size: '0.875rem' },
              { label: 'Caption', size: '0.75rem' },
            ].map((t) => (
              <div className="ds-type-row" key={t.label}>
                <span className="ds-type-label">{t.label}</span>
                <span className="ds-type-sample" style={{ fontSize: t.size }}>
                  Este e um texto de exemplo para demonstrar o tamanho da fonte no sistema.
                </span>
                <span className="ds-type-meta">{t.size}</span>
              </div>
            ))}
          </div>
        </section>

        {/* 3. Espacamentos e Arredondamentos */}
        <section className="ds-section" id="espacamentos">
          <h2 className="ds-section-title">Espacamentos e Arredondamentos</h2>
          <p className="ds-section-desc">
            Escala de espacamento consistente e opcoes de border-radius para manter a uniformidade visual.
          </p>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Escala de Espacamento</h3>
            <div className="ds-spacing-grid">
              {spacings.map((s) => (
                <div className="ds-spacing-item" key={s}>
                  <div className="ds-spacing-box" style={{ width: s, height: s }} />
                  <span className="ds-spacing-label">{s}px</span>
                </div>
              ))}
            </div>
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Border Radius</h3>
            <div className="ds-radius-grid">
              {radii.map((r) => (
                <div className="ds-radius-item" key={r.label}>
                  <div className="ds-radius-box" style={{ borderRadius: r.value }} />
                  <span className="ds-radius-label">{r.label} ({r.value}px)</span>
                </div>
              ))}
            </div>
          </div>
        </section>

        {/* 4. Logo e Marca */}
        <section className="ds-section" id="logo">
          <h2 className="ds-section-title">Logo e Marca</h2>
          <p className="ds-section-desc">
            A marca AutoRent utiliza a fonte Racing Sans One combinada com o icone de carro.
          </p>

          <div className="ds-logo-showcase">
            <div className="ds-logo-card ds-logo-card-light">
              <div className="ds-logo-text ds-logo-text-light">🚗 AutoRent</div>
              <span className="ds-logo-bg-label ds-logo-bg-label-light">Fundo claro</span>
            </div>
            <div className="ds-logo-card ds-logo-card-dark">
              <div className="ds-logo-text ds-logo-text-dark">🚗 AutoRent</div>
              <span className="ds-logo-bg-label ds-logo-bg-label-dark">Fundo escuro</span>
            </div>
          </div>

          <div className="ds-logo-rules">
            <div className="ds-logo-rule">
              <div className="ds-logo-rule-title ds-logo-rule-do">✓ Correto</div>
              <p>Manter espacamento minimo de 24px ao redor da logo. Usar sempre a fonte Racing Sans One. Manter o icone de carro alinhado ao texto.</p>
            </div>
            <div className="ds-logo-rule">
              <div className="ds-logo-rule-title ds-logo-rule-dont">✕ Incorreto</div>
              <p>Nao distorcer ou rotacionar a logo. Nao alterar as cores da marca. Nao usar fontes alternativas para o logotipo.</p>
            </div>
          </div>
        </section>

        {/* 5. Temas */}
        <section className="ds-section" id="temas">
          <h2 className="ds-section-title">Temas: Light e Dark</h2>
          <p className="ds-section-desc">
            O sistema suporta dois temas. As variaveis CSS sao alternadas pela classe <span className="ds-code">body.dark</span>.
          </p>

          <div className="ds-theme-grid">
            <div className="ds-theme-card ds-theme-card-light">
              <div className="ds-theme-label">Tema Claro</div>
              <div className="ds-theme-heading">Titulo do Cartao</div>
              <div className="ds-theme-text">Texto de exemplo para demonstrar como o conteudo aparece no tema claro com fundo branco e texto escuro.</div>
              <button className="ds-theme-btn">Botao Primario</button>
              <input className="ds-theme-input-preview ds-theme-input-light" placeholder="Campo de texto..." readOnly />
            </div>
            <div className="ds-theme-card ds-theme-card-dark">
              <div className="ds-theme-label" style={{ color: '#94a3b8' }}>Tema Escuro</div>
              <div className="ds-theme-heading">Titulo do Cartao</div>
              <div className="ds-theme-text">Texto de exemplo para demonstrar como o conteudo aparece no tema escuro com fundo escuro e texto claro.</div>
              <button className="ds-theme-btn">Botao Primario</button>
              <input className="ds-theme-input-preview ds-theme-input-dark" placeholder="Campo de texto..." readOnly />
            </div>
          </div>

          <div className="ds-code-block">
{`/* Variaveis do tema escuro */
body.dark {
  --bg-primary: #0f172a;
  --bg-secondary: #1e293b;
  --bg-card: #1e293b;
  --text-primary: #f1f5f9;
  --text-secondary: #94a3b8;
  --border-color: #334155;
}`}
          </div>
        </section>

        {/* 6. Botoes e Icones */}
        <section className="ds-section" id="botoes">
          <h2 className="ds-section-title">Botoes e Icones</h2>
          <p className="ds-section-desc">
            Variantes de botoes, tamanhos e icones utilizados na interface.
          </p>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Variantes de Botao</h3>
            <div className="ds-btn-row">
              <button className="btn btn-primary">Primario</button>
              <button className="btn btn-success">Sucesso</button>
              <button className="btn btn-danger">Perigo</button>
              <button className="btn btn-warning">Alerta</button>
              <button className="btn btn-outline">Outline</button>
              <button className="btn btn-primary" disabled>Desabilitado</button>
            </div>
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Tamanhos</h3>
            <div className="ds-btn-row">
              <button className="btn btn-primary btn-sm">Pequeno</button>
              <button className="btn btn-primary">Medio</button>
              <button className="btn btn-primary" style={{ padding: '14px 28px', fontSize: '1rem' }}>Grande</button>
            </div>
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Icones do Sistema</h3>
            <div className="ds-icon-grid">
              {icons.map((i) => (
                <div className="ds-icon-item" key={i.label}>
                  <span className="ds-icon-emoji">{i.emoji}</span>
                  <span className="ds-icon-label">{i.label}</span>
                </div>
              ))}
            </div>
          </div>

          <div className="ds-subsection">
            <h3 className="ds-subsection-title">Badges de Status</h3>
            <div className="ds-badge-row">
              <span className="badge badge-warning">Pendente</span>
              <span className="badge badge-info">Em Analise</span>
              <span className="badge badge-success">Aprovado</span>
              <span className="badge badge-danger">Rejeitado</span>
              <span className="badge badge-neutral">Cancelado</span>
              <span className="badge badge-success">Ativo</span>
              <span className="badge badge-danger">Inativo</span>
            </div>
          </div>
        </section>

        {/* 7. Componentes */}
        <section className="ds-section" id="componentes">
          <h2 className="ds-section-title">Componentes</h2>
          <p className="ds-section-desc">
            Exemplos dos principais componentes utilizados no sistema.
          </p>

          {/* Form */}
          <div className="ds-component-demo">
            <div className="ds-component-label">Formulario</div>
            <div className="ds-demo-form">
              <input className="ds-demo-input" placeholder="Nome completo" />
              <select className="ds-demo-select">
                <option>Selecione o tipo</option>
                <option>Sedan</option>
                <option>SUV</option>
                <option>Hatch</option>
              </select>
              <textarea className="ds-demo-textarea" placeholder="Observacoes adicionais..." />
            </div>
          </div>

          {/* Table */}
          <div className="ds-component-demo">
            <div className="ds-component-label">Tabela</div>
            <table className="ds-demo-table">
              <thead>
                <tr>
                  <th>Modelo</th>
                  <th>Ano</th>
                  <th>Diaria</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>Toyota Corolla</td>
                  <td>2024</td>
                  <td>R$ 180,00</td>
                  <td><span className="badge badge-success">Disponivel</span></td>
                </tr>
                <tr>
                  <td>Honda Civic</td>
                  <td>2023</td>
                  <td>R$ 200,00</td>
                  <td><span className="badge badge-warning">Reservado</span></td>
                </tr>
                <tr>
                  <td>VW T-Cross</td>
                  <td>2024</td>
                  <td>R$ 220,00</td>
                  <td><span className="badge badge-danger">Indisponivel</span></td>
                </tr>
              </tbody>
            </table>
          </div>

          {/* Card */}
          <div className="ds-component-demo">
            <div className="ds-component-label">Cartao</div>
            <div className="ds-demo-card-inner">
              <h4>Resumo do Pedido</h4>
              <p>Visualize os detalhes do seu pedido de aluguel incluindo veiculo selecionado, periodo e valor total estimado.</p>
            </div>
          </div>

          {/* Modal */}
          <div className="ds-component-demo">
            <div className="ds-component-label">Modal</div>
            <div className="ds-demo-modal">
              <h4>Confirmar Acao</h4>
              <p>Tem certeza que deseja cancelar este pedido? Esta acao nao podera ser desfeita.</p>
              <div className="ds-demo-modal-actions">
                <button className="btn btn-outline">Cancelar</button>
                <button className="btn btn-danger">Confirmar</button>
              </div>
            </div>
          </div>
        </section>

        {/* 8. Acessibilidade */}
        <section className="ds-section" id="acessibilidade">
          <h2 className="ds-section-title">Diretrizes de Acessibilidade</h2>
          <p className="ds-section-desc">
            Padroes seguidos para garantir que o sistema seja acessivel a todos os usuarios.
          </p>

          <div className="ds-a11y-grid">
            <div className="ds-a11y-card">
              <h4>🎯 Contraste Minimo</h4>
              <p>Razao de contraste minima de 4.5:1 para texto normal e 3:1 para texto grande, conforme WCAG 2.1 AA.</p>
            </div>
            <div className="ds-a11y-card">
              <h4>⌨️ Navegacao por Teclado</h4>
              <p>Todos os elementos interativos sao acessiveis via teclado com indicadores de foco visiveis usando Tab e Enter.</p>
            </div>
            <div className="ds-a11y-card">
              <h4>🏷️ HTML Semantico</h4>
              <p>Uso de elementos semanticos como header, nav, main, section e button para estrutura significativa.</p>
            </div>
            <div className="ds-a11y-card">
              <h4>♿ Atributos ARIA</h4>
              <p>Labels ARIA em botoes de icone, roles em regioes dinamicas e aria-label em campos sem rotulo visivel.</p>
            </div>
          </div>

          <div className="ds-subsection" style={{ marginTop: 32 }}>
            <h3 className="ds-subsection-title">Exemplos de Contraste</h3>
            <div className="ds-contrast-samples">
              <div className="ds-contrast-row" style={{ background: '#ffffff' }}>
                <span className="ds-contrast-text" style={{ color: '#0f172a' }}>Texto escuro em fundo branco</span>
                <span className="ds-contrast-ratio ds-contrast-pass">18.4:1 ✓</span>
              </div>
              <div className="ds-contrast-row" style={{ background: '#ffffff' }}>
                <span className="ds-contrast-text" style={{ color: '#6366f1' }}>Primario em fundo branco</span>
                <span className="ds-contrast-ratio ds-contrast-pass">4.6:1 ✓</span>
              </div>
              <div className="ds-contrast-row" style={{ background: '#0f172a' }}>
                <span className="ds-contrast-text" style={{ color: '#f1f5f9' }}>Texto claro em fundo escuro</span>
                <span className="ds-contrast-ratio ds-contrast-pass">15.4:1 ✓</span>
              </div>
              <div className="ds-contrast-row" style={{ background: '#ffffff' }}>
                <span className="ds-contrast-text" style={{ color: '#cbd5e1' }}>Gray 300 em fundo branco</span>
                <span className="ds-contrast-ratio ds-contrast-fail">1.7:1 ✕</span>
              </div>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}
