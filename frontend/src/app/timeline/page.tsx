"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { api, type JavaVersion } from "@/lib/api";

export default function TimelinePage() {
  const [versions, setVersions] = useState<JavaVersion[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.getVersions().then((data) => {
      setVersions(data);
      setLoading(false);
    }).catch(() => setLoading(false));
  }, []);

  return (
    <section className="section" id="timeline-page">
      <div className="container">
        <div className="section-header">
          <div className="section-label">📜 History</div>
          <h1 className="section-title">The Java Timeline</h1>
          <p className="section-description">
            Trace Java&apos;s evolution from Oak to Java 26 — three decades of innovation, performance, and developer empowerment.
          </p>
        </div>

        {/* Pre-history */}
        <div className="timeline">
          <div className="timeline-item animate-fade-in-up" style={{ animationDelay: "0s" }}>
            <div className="timeline-dot" />
            <div className="timeline-content">
              <div className="timeline-year">1991</div>
              <div className="timeline-title">Project Oak Begins</div>
              <div className="timeline-desc">
                James Gosling, Mike Sheridan, and Patrick Naughton initiate the &ldquo;Green Project&rdquo; at Sun Microsystems.
                The language was originally called &ldquo;Oak&rdquo; after an oak tree outside Gosling&apos;s office.
              </div>
            </div>
          </div>

          <div className="timeline-item animate-fade-in-up" style={{ animationDelay: "0.1s" }}>
            <div className="timeline-dot" />
            <div className="timeline-content">
              <div className="timeline-year">1995</div>
              <div className="timeline-title">Java is Born</div>
              <div className="timeline-desc">
                Renamed from Oak to Java (after Java coffee ☕). Sun Microsystems publicly announces Java at
                SunWorld &apos;95. The tagline &ldquo;Write Once, Run Anywhere&rdquo; (WORA) captures its cross-platform vision.
              </div>
            </div>
          </div>

          {/* Version Timeline */}
          {loading ? (
            <div className="loading-container">
              <div className="loading-spinner" />
            </div>
          ) : (
            versions.map((v, index) => (
              <div
                key={v.id}
                className="timeline-item animate-fade-in-up"
                style={{ animationDelay: `${(index + 2) * 0.05}s` }}
              >
                <div className={`timeline-dot ${v.lts ? "lts" : ""}`} />
                <Link href={`/versions/${v.id}`} className="timeline-content">
                  <div className="timeline-year">{v.releaseDate}</div>
                  <div className="timeline-title" style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                    {v.releaseName}
                    {v.lts && <span className="badge badge-lts">LTS</span>}
                    {v.codename && (
                      <span style={{ fontSize: "0.75rem", color: "var(--text-muted)", fontWeight: 400 }}>
                        ({v.codename})
                      </span>
                    )}
                  </div>
                  <div className="timeline-desc">
                    {v.description.length > 200
                      ? v.description.slice(0, 200) + "..."
                      : v.description}
                  </div>
                  <div className="timeline-features-list">
                    {v.keyFeatures.slice(0, 3).map((f, i) => (
                      <span key={i} className="feature-tag">{f}</span>
                    ))}
                  </div>
                </Link>
              </div>
            ))
          )}

          {/* Oracle Acquisition */}
          <div className="timeline-item animate-fade-in-up">
            <div className="timeline-dot" style={{ background: "var(--accent-purple)" }} />
            <div className="timeline-content">
              <div className="timeline-year">Key Milestone</div>
              <div className="timeline-title">Oracle Acquires Sun (2010)</div>
              <div className="timeline-desc">
                Oracle Corporation acquires Sun Microsystems for $7.4 billion,
                becoming the steward of Java. The six-month release cadence begins with Java 9 in 2017.
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
