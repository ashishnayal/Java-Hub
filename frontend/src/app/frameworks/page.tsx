"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { api, type Framework } from "@/lib/api";

const categoryIcons: Record<string, string> = {
  Web: "🌐",
  ORM: "🗄️",
  Security: "🔐",
  Testing: "🧪",
  Messaging: "📨",
  Build: "🔧",
  Architecture: "🏗️",
  Logging: "📋",
};

export default function FrameworksPage() {
  const [frameworks, setFrameworks] = useState<Framework[]>([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState("all");

  useEffect(() => {
    api.getFrameworks().then((data) => {
      setFrameworks(data);
      setLoading(false);
    }).catch(() => setLoading(false));
  }, []);

  const categories = ["all", ...Array.from(new Set(frameworks.map((f) => f.category)))];

  const filtered = filter === "all" ? frameworks : frameworks.filter((f) => f.category === filter);

  return (
    <section className="section" id="frameworks-page">
      <div className="container">
        <div className="section-header">
          <div className="section-label">🛠️ Ecosystem</div>
          <h1 className="section-title">Java Frameworks & Tools</h1>
          <p className="section-description">
            Deep-dive into the frameworks that power modern Java applications — from Spring Boot to Apache Kafka.
          </p>
        </div>

        <div className="filter-bar" style={{ justifyContent: "center" }}>
          {categories.map((cat) => (
            <button
              key={cat}
              className={`filter-btn ${filter === cat ? "active" : ""}`}
              onClick={() => setFilter(cat)}
            >
              {cat === "all" ? `All (${frameworks.length})` : `${categoryIcons[cat] || "📦"} ${cat}`}
            </button>
          ))}
        </div>

        {loading ? (
          <div className="loading-container">
            <div className="loading-spinner" />
          </div>
        ) : (
          <div className="cards-grid cards-grid-3 stagger-children">
            {filtered.map((f) => (
              <Link
                href={`/frameworks/${f.slug}`}
                key={f.id}
                className="card framework-card animate-fade-in-up"
              >
                <div className={`framework-icon ${f.category.toLowerCase()}`}>
                  {categoryIcons[f.category] || "📦"}
                </div>
                <h3 className="framework-name">{f.name}</h3>
                <div className="framework-category">{f.category}</div>
                <p className="framework-desc">{f.description}</p>
                <div style={{ marginTop: "1rem", display: "flex", flexWrap: "wrap", gap: "4px" }}>
                  {f.keyFeatures?.slice(0, 3).map((feat, i) => (
                    <span key={i} className="feature-tag">{feat}</span>
                  ))}
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
