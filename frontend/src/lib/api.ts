const API_BASE = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";

async function fetchApi<T>(endpoint: string): Promise<T> {
  const res = await fetch(`${API_BASE}${endpoint}`, {
    cache: "no-store",
  });
  if (!res.ok) {
    throw new Error(`API error: ${res.status} ${res.statusText}`);
  }
  return res.json();
}

export interface CodeExample {
  title: string;
  language: string;
  code: string;
  explanation: string;
}

export interface JavaVersion {
  id: string;
  versionNumber: number;
  releaseName: string;
  releaseDate: string;
  lts: boolean;
  codename: string;
  description: string;
  keyFeatures: string[];
  codeExamples: CodeExample[];
}

export interface Topic {
  id: string;
  title: string;
  slug: string;
  category: string;
  description: string;
  content: string;
  codeExamples: CodeExample[];
  interviewQuestions: string[];
  difficulty: string;
  tags: string[];
}

export interface Framework {
  id: string;
  name: string;
  slug: string;
  category: string;
  description: string;
  overview: string;
  keyFeatures: string[];
  gettingStarted: string;
  codeExamples: CodeExample[];
  useCases: string[];
  officialUrl: string;
}

export interface SearchResults {
  versions: JavaVersion[];
  topics: Topic[];
  frameworks: Framework[];
  totalResults: number;
}

// API Functions
export const api = {
  getVersions: () => fetchApi<JavaVersion[]>("/versions"),
  getVersion: (id: string) => fetchApi<JavaVersion>(`/versions/${id}`),
  getVersionByNumber: (num: number) => fetchApi<JavaVersion>(`/versions/number/${num}`),

  getTopics: (category?: string) =>
    fetchApi<Topic[]>(category ? `/topics?category=${category}` : "/topics"),
  getTopic: (slug: string) => fetchApi<Topic>(`/topics/${slug}`),

  getFrameworks: (category?: string) =>
    fetchApi<Framework[]>(category ? `/frameworks?category=${category}` : "/frameworks"),
  getFramework: (slug: string) => fetchApi<Framework>(`/frameworks/${slug}`),

  search: (query: string) => fetchApi<SearchResults>(`/search?q=${encodeURIComponent(query)}`),
};
